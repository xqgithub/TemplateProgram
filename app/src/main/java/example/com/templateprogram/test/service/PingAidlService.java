package example.com.templateprogram.test.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import example.com.templateprogram.aidl.IPingAidlInterface;
import example.com.templateprogram.entity.PingResponse;
import example.com.templateprogram.utils.LogUtils;
import example.com.templateprogram.utils.StringUtils;

/**
 * Created by beijixiong on 2018/12/22.
 */

public class PingAidlService extends Service {


    IPingAidlInterface.Stub mStub = new IPingAidlInterface.Stub() {
        @Override
        public String getPingTime(String url) throws RemoteException {
//            startToPing(url);
            return "";
        }

        @Override
        public Bundle getPingTime2(String url) throws RemoteException {
            return startToPing(url);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.i(" =-= onCreate");
        list = new ArrayList<>();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mStub;
    }

    private List<PingResponse> list;


    /**
     * 开始去ping
     */
    public Bundle startToPing(String url) {
        Bundle bundle = new Bundle();
        final String url1 = urlConversion(url);
        ExecutorService cachedthreadpool = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            cachedthreadpool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Ping(url1);
                    } catch (Exception e) {
                        LogUtils.e(e.getMessage());
                    }
                }
            });
        }
        cachedthreadpool.shutdown();
        //判断线程是否执行完毕
        while (true) {
            if (cachedthreadpool.isTerminated()) {
                if (!StringUtils.isBlank(list) &&
                        list.size() > 0) {
                    LogUtils.i("最小的ping =-= " + minPing(list));
                    LogUtils.i("最大的ping =-= " + maxPing(list));
                    LogUtils.i("平均的ping =-= " + avgPing(list));
                    LogUtils.i("丢包率 =-= " + packetLoss(list));
//                    result = minPing(list) + "||"
//                            + maxPing(list) + "||"
//                            + avgPing(list) + "||"
//                            + packetLoss(list);
                    bundle.putInt("minPing", minPing(list));
                    bundle.putInt("maxPing", maxPing(list));
                    bundle.putInt("avgPing", avgPing(list));
                    bundle.putString("packetLoss", packetLoss(list));

                    list.clear();
                }
                break;
            }
        }
        return bundle;
    }

    /**
     * ping方法
     *
     * @param url
     * @return
     */
    public String Ping(String url) {
        String resault = "";
        Process p;
        try {
            //ping -c 1 -w 4  中  ，-c 是指ping的次数 3是指ping 3次 ，-w 4  以秒为单位指定超时间隔，是指超时时间为4秒
            p = Runtime.getRuntime().exec("ping -c 1 -w 4 " + url);
            int status = p.waitFor();
            // 读取ping的内容，可不加
            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer stringBuffer = new StringBuffer();
            String content = "";
            while ((content = in.readLine()) != null) {
                stringBuffer.append(content);
            }
            resault = stringBuffer.toString();
            System.out.println("Return ============" + stringBuffer.toString());
            PingResponse pingresponse = new PingResponse();
            if (!StringUtils.isBlank(resault)) {
                //丢包率
                String packet_loss = resault.substring(stringBuffer.toString().toLowerCase().indexOf("received,") + 9,
                        stringBuffer.toString().toLowerCase().indexOf("packet loss")).trim();
                if ("0%".equals(packet_loss)) {
                    pingresponse.setDomain(url);
                    pingresponse.setPing_time(getPingTime(stringBuffer.toString()));
                } else {
                    pingresponse.setDomain(url);
                    pingresponse.setPing_time(-1);
                }
            } else {
                pingresponse.setDomain(url);
                pingresponse.setPing_time(-1);
            }
            list.add(pingresponse);


            if (status == 0) {
                resault = "success";
            } else {
                resault = "faild";
            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
        return resault;
    }


    /**
     * 获取每次ping的时间
     */
    public int getPingTime(String result) {
        String pingtime = "";
        int _pingtime = 0;
        try {
            result.indexOf("time");
            result.indexOf("ms");
            pingtime = result.substring(result.toLowerCase().indexOf("time") + 5,
                    result.toLowerCase().indexOf("ms")).trim();
            //取整
            DecimalFormat df = new DecimalFormat("#");
            //重新赋值
            pingtime = df.format(Double.valueOf(pingtime));
            _pingtime = Integer.parseInt(pingtime);
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
        return _pingtime;
    }

    /**
     * 得到最小的ping时间
     */
    public int minPing(List<PingResponse> list) {
        int min_ping = 7777777;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPing_time() != -1) {
                if (min_ping > list.get(i).getPing_time()) {
                    min_ping = list.get(i).getPing_time();
                }
            }
        }
        //表示所有的时间都是超时
        if (min_ping == 7777777) {
            min_ping = 0;
        }
        return min_ping;
    }

    /**
     * 得到最大的ping时间
     */
    public int maxPing(List<PingResponse> list) {
        int max_ping = -7777777;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPing_time() != -1) {
                if (max_ping < list.get(i).getPing_time()) {
                    max_ping = list.get(i).getPing_time();
                }
            }
        }
        //表示所有的时间都是超时
        if (max_ping == -7777777) {
            max_ping = 0;
        }
        return max_ping;
    }

    /**
     * 得到ping的平均值
     */
    public int avgPing(List<PingResponse> list) {
        int avg_ping = 0;
        int growth_num = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPing_time() != -1) {
                avg_ping = avg_ping + list.get(i).getPing_time();
            } else {
                growth_num = growth_num + 1;
            }
        }
        if (growth_num == list.size()) {
            avg_ping = 0;
        } else {
            avg_ping = avg_ping / (list.size() - growth_num);
        }
        return avg_ping;
    }

    /**
     * 得到全部的ping中的丢包率
     */
    public String packetLoss(List<PingResponse> list) {
        int packet_Loss = 0;
        int growth_num = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPing_time() == -1) {
                growth_num = growth_num + 1;
            }
        }
        packet_Loss = (growth_num / list.size()) * 100;
        return packet_Loss + "%";
    }

    /**
     * 域名做处理
     * 去掉https或者http，以及结尾的/
     */
    private String urlConversion(String url) {
        String url_conversion = "";
        url_conversion = url.replaceAll("https:", "");
        url_conversion = url_conversion.replaceAll("http:", "");
        url_conversion = url_conversion.replaceAll("/", "");
        return url_conversion;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i(" =-= onDestroy");
    }
}
