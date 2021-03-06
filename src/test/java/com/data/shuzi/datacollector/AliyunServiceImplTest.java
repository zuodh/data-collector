package com.data.shuzi.datacollector;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.data.shuzi.datacollector.dao.DeviceRealDAO;
import com.data.shuzi.datacollector.event.DataEvent;
import com.data.shuzi.datacollector.event.DataEventProducer;
import com.data.shuzi.datacollector.event.DisruptorStart;
import com.data.shuzi.datacollector.service.AliyunService;
import com.data.shuzi.datacollector.service.ProjectService;
import com.data.shuzi.datacollector.service.impl.AliyunServiceImpl;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import javafx.scene.input.DataFormat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author zizuo.zdh
 * @ClassName AliyunServiceImplTest
 * @Description TODO
 * @Date 2018/6/26 11:34
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class AliyunServiceImplTest {
    @Autowired
    AliyunService aliyunService;
    @Autowired
    DeviceRealDAO deviceRealDAO;

    @Before
    public void init() {
        DisruptorStart.getInstance();
    }

    @Test
    public void test1() throws Exception {
        AliyunServiceImpl aliyunService = new AliyunServiceImpl();
        // System.out.println(aliyunService.getValueWhenExpired("aliyun"));
        System.out.println(aliyunService.getAliyunToken());
    }

    @Test
    public void test2() {
        AliyunServiceImpl aliyunService = new AliyunServiceImpl();
        JSONObject jsonObject = aliyunService.getProjectAlarm();
        System.out.println(jsonObject);
    }

    @Test
    public void test3() {
        String deviceId = "1434462209";
        String itemId = "155";
        JSONObject object = aliyunService.getHistoryData(deviceId, itemId, System.currentTimeMillis() / 1000);
        System.out.println(JSON.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect));
    }

    @Test
    public void test4() throws IOException, InterruptedException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("C:\\Users\\zizuo.zdh\\Desktop\\shuanglianghis2222.txt")));
        int[] projArr = new int[]{2, 213, 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 239, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 254, 255, 256, 257, 258, 259, 260, 261, 262, 263, 264, 265, 266, 267, 268, 269, 270, 271, 272, 273, 274, 275, 276, 277, 278, 279, 280, 281, 282, 283, 284, 285, 286, 287, 288, 289, 290, 291, 292, 293, 294, 295, 296, 297, 298, 299, 300, 301, 302, 303, 304, 305, 306, 307, 308, 309, 310, 311, 312, 313, 314, 315, 316, 317, 318, 319, 320, 321, 322, 323, 324, 325, 327, 328, 329, 330, 331, 332, 333, 334, 335, 336, 337, 338, 339, 340, 341, 342, 343, 344, 345, 346, 347, 348, 349, 350, 351, 352, 353, 354, 355, 356, 357, 358, 359, 360, 361, 362, 363, 364, 365, 366, 367, 368, 369, 370, 371, 372, 373, 374, 375, 376, 377, 378, 379, 380, 381, 382, 383, 384, 385, 386, 387, 388, 389, 390, 391, 392, 393, 394, 395, 396, 397, 398, 399, 400, 401, 402, 403, 404, 405, 406, 407, 408, 409, 410, 411, 412, 413, 414, 415, 416, 417, 418, 419, 420, 421, 422, 423, 424, 425, 426, 427, 428, 429, 430, 431, 432, 433, 434, 435, 436, 437, 438, 439, 440, 441, 442, 443, 444, 445, 446, 447, 448, 449, 450, 451, 452, 453, 454, 455, 456, 457, 458, 459, 460, 461, 462, 463, 464, 465, 466, 467, 468, 469, 470, 471, 472, 473, 474, 475, 476, 477, 478, 479, 480, 481, 482, 483, 484, 485, 486, 487, 488, 489, 490, 491, 492, 493, 494, 495, 496, 497, 498, 499, 500, 501, 502, 503, 504, 505, 506, 507, 508, 509, 510, 511, 512, 513, 514, 515, 516, 517, 518, 519, 520, 521, 522, 523, 524, 525, 526, 527, 528, 529, 530, 531, 532, 533, 534, 535, 536, 537, 538, 539, 540, 541, 542, 543, 544, 545, 546, 547, 548, 549, 550, 551, 552, 553, 554, 555, 556, 557, 558, 559, 560, 561, 562, 563, 564, 565, 566, 567, 568, 569, 570, 571, 572, 573, 574, 575, 576, 577, 578, 579, 580, 581, 582, 583, 584, 585, 586, 587, 588, 589, 590, 591, 592, 593, 594, 595, 596, 597, 598, 599, 600, 601, 602, 603, 604, 605, 606, 607, 608, 609, 610, 611, 612, 613, 614, 615, 616, 617, 618, 619, 620, 621, 622, 623, 624, 625, 626, 627, 628, 629, 630, 631, 632, 633, 634, 635, 636, 637, 638, 639, 640, 641, 642, 643, 644, 645, 646, 647, 648, 649, 650, 651, 652, 653, 654, 655, 656, 657, 658, 659, 660, 661, 662, 663, 664, 665, 666, 667, 668, 669, 670, 671, 672, 673, 674, 675, 676, 677, 678, 679, 680, 681, 682, 683, 684, 685, 686, 687, 688, 689, 690, 691, 692, 693, 694, 695, 696, 697, 698, 699, 700, 701, 702, 703, 704, 705, 706, 707, 708, 709, 710, 711, 712, 713, 714, 715, 716, 717, 718, 719, 720, 721, 722, 723, 724, 725, 726, 727, 728, 729, 730, 731, 732, 733, 734, 735, 736, 737, 738, 739, 740, 741, 742, 743, 744, 745, 746, 747, 748, 749, 750, 751, 752, 753, 754, 755, 756, 757, 758, 759, 760, 761, 762, 763, 764, 765, 766, 767, 768, 769, 770, 771, 772, 773, 774, 775, 776, 777, 778, 779, 780, 781, 782, 783, 784, 785, 786, 787, 788, 789, 790, 791, 792, 793, 794, 795, 796, 797, 798, 799, 800, 801, 802, 803, 804, 805, 806, 807, 808, 809, 810, 811, 812, 813, 814, 815, 816, 817, 818, 819, 820, 821, 822, 823, 824, 825, 826, 827, 828, 829, 830, 831, 834, 835, 836, 837, 840, 841, 842, 866, 886, 887, 888, 889, 894, 897, 907, 908, 911, 912, 913, 914, 915, 926, 933, 935, 936, 938, 940, 941, 942, 943, 949, 950, 952, 954, 955, 956, 957, 958, 959, 960, 963, 964, 965, 966, 967, 987};
        for (int i = 0; i < projArr.length; i++) {
            JSONObject jsonObject = aliyunService.getProjectCurrentItemData(String.valueOf(projArr[i]));
            if (jsonObject == null) {
                continue;
            }
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            if (jsonArray == null || jsonArray.size() == 0) {
                continue;
            }
            for (int j = 0; j < jsonArray.size(); j++) {
                JSONObject itemObj = jsonArray.getJSONObject(j);
                if (itemObj == null) {
                    continue;
                }
                String deviceId = itemObj.getString("devid");
                String itemId = itemObj.getString("itemid");
                JSONObject object = aliyunService.getHistoryData(deviceId, itemId, System.currentTimeMillis() / 1000);
                bufferedWriter.write(deviceId + "->" + itemId + "->" + JSON.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect));
                bufferedWriter.write("\n");
                bufferedWriter.flush();
                //Thread.sleep(5000);
            }
        }
        bufferedWriter.close();
    }

    @Test
    public void test5() throws IOException {
        List<String> list = new ArrayList<>();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("C:\\Users\\zizuo.zdh\\Desktop\\shuanglianghis3333.txt")));
        BufferedReader br = new BufferedReader(new FileReader(new File("C:\\Users\\zizuo.zdh\\Desktop\\deviceId.txt")));
        while (br.ready()) {
            String line = br.readLine();
            if (StringUtils.isEmpty(line)) {
                continue;
            }
            list.add(line.trim());
        }
        List<Map<String, Object>> retList = deviceRealDAO.queryProjectAndItem();
        if (retList == null || retList.size() == 0) {
            return;
        }
        retList.forEach(e -> {
            String devid = (String) e.get("devid");
            String itemId = (String) e.get("itemId");
            String tmp = devid + "->" + itemId;
            if (!list.contains(tmp)) {
                JSONObject object = aliyunService.getHistoryData(devid, itemId, System.currentTimeMillis() / 1000);
                try {
                    bufferedWriter.write(devid + "->" + itemId + "->" + JSON.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect));
                    bufferedWriter.write("\n");
                    bufferedWriter.flush();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });
        bufferedWriter.close();
    }

    @Test
    public void test6() {
        JSONObject jsonObject = aliyunService.getProjectAlarm();
        if (jsonObject != null) {
            System.out.println(jsonObject);
        }
        JSONArray json = jsonObject.getJSONArray("data");
        if (json != null && json.size() > 0) {
            for (int i = 0; i < json.size(); i++) {
                JSONObject jsonoBject = json.getJSONObject(i);
                String address = jsonoBject.getString("address");
                if (address.indexOf("中山") != -1 || address.indexOf("互太") != -1) {
                    System.out.println(jsonoBject);
                }
            }
        }

    }

    @Test
    public void test7() throws IOException, ParseException {
        List<Integer> list = Lists.newArrayList(324, 325, 365, 328, 840, 841, 842, 886, 897, 915);
        Disruptor<DataEvent> disruptor=DisruptorStart.getDisruptor();
        RingBuffer<DataEvent> ringBuffer = disruptor.getRingBuffer();
        DataEventProducer producer = new DataEventProducer(ringBuffer);
        list.forEach(e -> {
            JSONObject jsonObject = aliyunService.getProjectCurrentItemData(String.valueOf(e));
            if (jsonObject != null && jsonObject.size() > 0) {
                for (int i = 0; i < jsonObject.size(); i++) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (jsonArray == null || jsonArray.size() == 0) {
                        continue;
                    }
                    for (int j = 0; j < jsonArray.size(); j++) {
                        JSONObject json1 = jsonArray.getJSONObject(j);
                        String devId = json1.getString("devid");
                        String itemId = json1.getString("itemid");
                        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        Date date = null;
                        try {
                            date = dataFormat.parse("2018-06-24 00:00:00");
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                        Calendar begin = Calendar.getInstance();
                        begin.setTime(date);
                        for (int k = 0; k < 168; k++) {
                            System.out.println("the current thread is:" + k);
                            JSONObject jsonObject1 = aliyunService.getHistoryData(devId, itemId, begin.getTime().getTime() / 1000);
                            jsonObject1.put("devId",devId);
                            jsonObject1.put("itemId",itemId);
                            producer.onData(jsonObject1);
                            begin.add(Calendar.HOUR, 1);
                        }
                    }
                }
            }
        });
    }

    @Test
    public void test8() throws IOException {
        Executor executor = Executors.newFixedThreadPool(4);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("C:\\Users\\zizuo.zdh\\Desktop\\historyData.txt")));

        //  JSONObject jsonObject=aliyunService.getHistoryData("1495093248","114",System.currentTimeMillis()/1000);
        //executor.execute(new HistoryDataExecutor(aliyunService,"1495093248", "114",bufferedWriter));
        //  System.out.println(jsonObject);
        System.out.println("the current thread is:" + Thread.currentThread().getName());
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = dataFormat.parse("2018-06-24 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar begin = Calendar.getInstance();
        begin.setTime(date);
        System.out.println("the current thread is:" + begin.getTime());

        for (int i = 0; i < 168; i++) {
            System.out.println("the current thread is:" + i);
            JSONObject jsonObject1 = aliyunService.getHistoryData("1495093248", "114", begin.getTime().getTime() / 1000);
            if (jsonObject1 == null) continue;
            System.out.println("the current thread is:" + jsonObject1);
            bufferedWriter.write(jsonObject1.toJSONString());
            bufferedWriter.write("\n");
            bufferedWriter.flush();
            begin.add(Calendar.HOUR, 1);
        }
        bufferedWriter.close();

    }
    @Test
    public void test9() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("C:\\Users\\zizuo.zdh\\Desktop\\realData.txt")));
        List<Integer> list = Lists.newArrayList(324, 325, 365, 328, 840, 841, 842, 886, 897, 915);
        list.forEach(e -> {
            JSONObject jsonObject = aliyunService.getProjectCurrentItemData(String.valueOf(e));
            if (jsonObject != null && jsonObject.size() > 0) {
                try {
                    bufferedWriter.write(e+"->"+jsonObject.toJSONString());
                    bufferedWriter.write("\n");
                    bufferedWriter.flush();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
    @Test
    public void test10() throws IOException {
        int count=0;
        Disruptor<DataEvent> disruptor=DisruptorStart.getDisruptor();
        RingBuffer<DataEvent> ringBuffer = disruptor.getRingBuffer();
        DataEventProducer producer = new DataEventProducer(ringBuffer);
        BufferedReader projBr = new BufferedReader(new FileReader(new File("C:\\Users\\zizuo.zdh\\Desktop\\project.txt")));
        BufferedReader br = new BufferedReader(new FileReader(new File("C:\\Users\\zizuo.zdh\\Desktop\\realData.txt")));
        Map<String,JSONArray> deviceMap=new HashMap<>();
        while (br.ready()) {
            String line = br.readLine();
            String[] arrt=line.split("->");
            JSONObject jsonObject = JSON.parseObject(arrt[1]);
            if (jsonObject == null) {
                continue;
            }
            JSONArray jsonArray=jsonObject.getJSONArray("data");
            deviceMap.put(arrt[0],jsonArray);
        }

        while (projBr.ready()) {
            String line = projBr.readLine();
            JSONObject jsonObject = JSON.parseObject(line);
            if (jsonObject == null) {
                continue;
            }
            Integer projId = jsonObject.getInteger("id");
            JSONArray jsonArray = deviceMap.get(String.valueOf(projId));
            if (jsonArray == null || jsonArray.size() == 0) {
                continue;
            }
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject json1 = jsonArray.getJSONObject(i);
                String devId = json1.getString("devid");
                String itemId = json1.getString("itemid");

                SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = null;
                try {
                    date = dataFormat.parse("2018-07-04 00:00:00");
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                Calendar begin = Calendar.getInstance();
                begin.setTime(date);
                for (int k = 0; k < 24; k++) {
                    JSONObject jsonObject1 = aliyunService.getHistoryData(devId, itemId, begin.getTime().getTime() / 1000);
                    jsonObject1.put("address",jsonObject.getString("address"));
                    jsonObject1.put("name",jsonObject.getString("name"));
                    jsonObject1.put("latitude",jsonObject.getDouble("latitude"));
                    jsonObject1.put("longitude",jsonObject.getDouble("longitude"));
                    jsonObject1.put("projId",projId);
                    jsonObject1.put("alias",json1.getString("alias"));
                    jsonObject1.put("dataAddress",json1.getString("dataAddress"));
                    jsonObject1.put("devid",json1.getString("devid"));
                    jsonObject1.put("itemid",json1.getString("itemid"));
                    jsonObject1.put("itemname",json1.getString("itemname"));
                    jsonObject1.put("readOnly",json1.getString("readOnly"));
                    jsonObject1.put("specificType",json1.getString("specificType"));
                    jsonObject1.put("vdeviceName",json1.getString("vdeviceName"));
                    jsonObject1.put("devName",json1.getString("devName"));
                    jsonObject1.put("quality",json1.getString("quality"));
                    jsonObject1.put("datatype",json1.getString("datatype"));
                    producer.onData(jsonObject1);
                    count++;
                    System.out.println("device history count:"+count);
                    begin.add(Calendar.HOUR, 1);
                }
            }
        }
    }
    @Test
    public void test11() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("C:\\Users\\zizuo.zdh\\Desktop\\zuodh.txt")));
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = dataFormat.parse("2018-07-04 14:00:00");
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        Calendar begin = Calendar.getInstance();
        begin.setTime(date);
        for(int i=0;i<350;i++) {
            JSONObject jsonObject1 = aliyunService.getHistoryData("1495093248", String.valueOf(i), begin.getTime().getTime() / 1000);
           // jsonObject1.getJSONArray();
            bufferedWriter.write(jsonObject1.toString());
            bufferedWriter.write("\n");
            bufferedWriter.flush();
        }
    }
}
