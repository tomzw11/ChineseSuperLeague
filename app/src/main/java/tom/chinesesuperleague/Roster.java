package tom.chinesesuperleague;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

public class Roster {

//    public static final Map<String,String> roster = new HashMap<String,String>(){
//
//        {
//            //天津泰达
//            put("113067","宗磊");
//            put("180532","杜佳");
//            put("113512","杨启鹏");
//            put("155932","杨泽翔");
//            put("80353","儒尼奥尔");
//            put("210564","潘喜明");
//            put("109441","约万诺维奇");
//            put("36476","周海滨");
//            put("102821","聊博超");
//            put("83759","白岳峰");
//            put("102959","聂涛");
//            put("113389","苑维玮");
//            put("27578","曹阳");
//            put("102816","李本舰");
//            put("102817","胡人天");
//            put("155951","王秋明");
//            put("185319","郭毅");
//            put("155935","郭皓");
//            put("13119","曲波");
//            put("185328","李源一");
//            put("113103","周通");
//            put("164011","蒂亚戈内");
//            put("57252","蒙特罗");
//            put("113509","惠家康");
//            put("144130","埃沃纳");
//            put("102961","范柏群");
//            put("155954","李志斌");
//            //广州恒大
//            put("156229","方镜淇");
//            put("61627","曾诚");
//            put("113495","刘殿座");
//            put("155966","梅方");
//            put("81801","张琳芃");
//            put("61630","冯潇霆");
//            put("223673","韩鹏飞");
//            put("98762","金英权");
//            put("61632","荣昊");
//            put("223975","吴裕多");
//            put("61635","李学鹏");
//            put("230381","胡睿宝");
//            put("205304","陈泽鹏");
//            put("156225","廖力生");
//            put("185324","徐新");
//            put("100534","保利尼奥");
//            put("27581","郑智");
//            put("61622","高拉特");
//            put("113074","张文钊");
//            put("86553","黄博文");
//            put("61642","于汉超");
//            put("113210","邹正");
//            put("113186","郑龙");
//            put("230434","张奥凯");
//            put("51565","阿兰");
//            put("77064","马丁内斯");
//            put("37441","郜林");
//            //江苏苏宁
//            put("113301","顾超");
//            put("113128","姜灏");
//            put("86529","张思鹏");
//            put("180465","李昂");
//            put("113231","周云");
//            put("102909","圣斯伯里");
//            put("113230","许有志");
//            put("113460","吴曦");
//            put("113240","任航");
//            put("77864","洪正好");
//            put("113271","杨笑天");
//            put("223575","刘伟");
//            put("53392","拉米雷斯");
//            put("81799","刘建业");
//            put("113300","谢鹏飞");
//            put("180468","张晓彬");
//            put("180470","曹康");
//            put("61641","杨昊");
//            put("180467","张新林");
//            put("113136","李智超");
//            put("113266","吉翔");
//            put("113496","杨家威");
//            put("223626","曹文");
//            put("39461","若");
//            put("52304","特谢拉");
//            put("180469","陶源");
//            put("168506","马丁内斯");
//            put("121175","戈伟");
//            //上海申花
//            put("113385","耿晓锋");
//            put("113462","邱盛炯");
//            put("27564","李帅");
//            put("113448","熊飞");
//            put("113211","李建滨");
//            put("120764","金基熙");
//            put("113165","李文博");
//            put("155919","李运秋");
//            put("113459","郑凯木");
//            put("120162","柏佳俊");
//            put("81803","邓卓翔");
//            put("113450","小王林");
//            put("113468","陶金");
//            put("113325","王寿挺");
//            put("113338","张璐");
//            put("77063","莫雷诺");
//            put("113439","吕征");
//            put("37399","瓜林");
//            put("113489","王赟");
//            put("113205","秦升");
//            put("113467","曹赟定");
//            put("212911","徐骏敏");
//            put("47412","登巴巴");
//            put("155897","战怡麟");
//            put("14934","马丁斯");
//            put("113386","高迪");
//            put("113336","毕津浩");
//            //上海上港
//            put("155917","颜骏凌");
//            put("155969","孙乐");
//            put("223356","施晓东");
//            put("180492","张卫");
//            put("155922","王燊超");
//            put("155924","汪佳捷");
//            put("155926","蔡慧康");
//            put("154693","金周荣");
//            put("180500","杨世元");
//            put("155938","傅欢");
//            put("113276","石柯");
//            put("180501","贺惯");
//            put("27568","孙祥");
//            put("113125","杨博宇");
//            put("116730","武磊");
//            put("61073","达维");
//            put("51566","孔卡");
//            put("180498","张一");
//            put("113485","王佳玉");
//            put("41337","于海");
//            put("223603","朱征宇");
//            put("180503","郑致云");
//            put("180505","孙峻岗");
//            put("33223","吉安");
//            put("61165","埃尔克森");
//            put("155947","吕文君");
//            put("180497","李浩文");
//            put("155934","朱峥嵘");
//            put("155958","林创益");
//            put("223602","胡靖航");
//            //河北华夏
//            put("223892","欧亚");
//            put("223897","苏维超");
//            put("113382","杨程");
//            put("223988","周煜辰");
//            put("113292","杜文洋");
//            put("113121","金洋洋");
//            put("13103","杜威");
//            put("185327","廖均健");
//            put("223901","芦洋");
//            put("46534","居吕姆");
//            put("223902","许小龙");
//            put("223987","杨文吉");
//            put("180519","丁海峰");
//            put("212909","高准翼");
//            put("229708","钟炬展");
//            put("113203","宋文杰");
//            put("223906","车世伟");
//            put("223904","章艮");
//            put("155888","罗森文");
//            put("61644","姜宁");
//            put("223894","贾晓琛");
//            put("223898","李浩然");
//            put("223895","汪泉");
//            put("223900","朱海威");
//            put("66752","汪洋");
//            put("59325","卡库塔");
//            put("27671","姆比亚");
//            put("155961","耿袈岂");
//            put("223896","张利峰");
//            put("223986","李行");
//            put("113109","姜文骏");
//            put("100817","阿洛伊西奥");
//            put("113119","董学升");
//            put("45154","拉维齐");
//            put("223985","桂宏");
//            put("43274","热尔维尼奥");
//            put("223899","高云飞");
//
//            //广州富力
//            put("113162","程月磊");
//            put("180452","张世昌");
//            put("113051","于洋");
//            put("113126","张耀坤");
//            put("185322","杨挺");
//            put("103782","张贤秀");
//            put("167852","付云龙");
//            put("223978","黄政宇");
//            put("51774","古斯塔夫-斯文松");
//            put("86556","王晓龙");
//            put("113481","姜至鹏");
//            put("223976","宁安");
//            put("113181","唐淼");
//            put("180790","常飞亚");
//            put("113187","卢琳");
//            put("56838","扎哈维");
//            put("223977","向嘉弛");
//            put("180528","侯俊杰");
//            put("113377","王嘉楠");
//            put("52667","雷纳迪尼奥");
//            put("116429","陈志钊");
//            put("113329","汪嵩");
//            put("223979","黎宇扬");
//            put("76266","吉安努");
//            put("113337","肖智");
//            //河南建业
//            put("205812","王国明");
//            put("155968","吴龑");
//            put("223941","魏鹏");
//            put("212910","李晓明");
//            put("192101","戈麦斯");
//            put("113352","顾操");
//            put("84745","萨马尔季奇");
//            put("113388","买提江");
//            put("47659","麦克格文");
//            put("113366","张可");
//            put("223989","罗恒");
//            put("213414","路尧");
//            put("213201","龙成");
//            put("156047","冯卓毅");
//            put("180460","尹鸿博");
//            put("116255","伊沃");
//            put("223939","牟善韬");
//            put("180459","杨阔");
//            put("223940","潘嘉俊");
//            put("223990","王飞");
//            put("180461","钟晋宝");
//            put("100925","帕蒂尼奥");
//            put("155965","梁雨");
//            put("114625","奥斯曼");
//            put("180466","张帅");
//            put("155892","陈灏");
//            //延边富德
//            put("223919","尹光");
//            put("223929","池文一");
//            put("155971","董佳林");
//            put("223936","李峻宇");
//            put("223920","韩轩");
//            put("113190","赵铭");
//            put("42596","彼得科维奇");
//            put("223921","姜洪权");
//            put("223924","吴永春");
//            put("223927","崔民");
//            put("223928","金贤");
//            put("223932","金弘宇");
//            put("224063","田依浓");
//            put("224062","李勋");
//            put("221344","韩光徽");
//            put("205896","池忠国");
//            put("155130","金胜大");
//            put("205897","崔仁");
//            put("223922","金波");
//            put("98766","尹比加林");
//            put("223923","王志鹏");
//            put("223925","朴世豪");
//            put("223926","李浩");
//            put("223930","裴育文");
//            put("223931","李浩杰");
//            put("223933","文学");
//            put("223934","艾合买提-艾克拜尔");
//            put("223935","尹昌吉");
//            put("223937","孙君");
//            put("202915","特拉沃利");
//            put("223918","河太均");
//            //北京国安
//            put("61626","杨智");
//            put("86548","侯森");
//            put("205009","张岩");
//            put("155003","克里梅茨");
//            put("113474","李磊");
//            put("27567","周挺");
//            put("13107","徐云龙");
//            put("86530","郎征");
//            put("86557","张辛昕");
//            put("86561","杨运");
//            put("113060","雷腾龙");
//            put("113098","赵和靖");
//            put("223943","魏鑫");
//            put("230437","盛鹏飞");
//            put("61034","拉尔夫");
//            put("102957","张晓彬");
//            put("205797","张池明");
//            put("113066","朴成");
//            put("113053","张稀哲");
//            put("113454","宋博轩");
//            put("156056","杜明洋");
//            put("46546","于大宝");
//            put("51538","雷纳托-奥古斯托");
//            put("113056","李翰博");
//            put("154690","谢尔盖耶夫");
//            put("39931","伊尔马兹");
//            put("223942","单欢欢");
//            put("80723","张呈栋");
//            //重庆力帆
//            put("113227","邓小飞");
//            put("223949","王敏");
//            put("223950","陈安琪");
//            put("223946","李放");
//            put("113163","隋东陆");
//            put("113244","丁捷");
//            put("113108","刘宇");
//            put("85304","米洛维奇");
//            put("61628","陈雷");
//            put("113353","谭望嵩");
//            put("37502","王栋");
//            put("113101","吴庆");
//            put("223947","郑毅");
//            put("201436","郑又荣");
//            put("113355","徐洋");
//            put("113213","彭欣力");
//            put("168101","费尔南多");
//            put("223948","张诚");
//            put("113084","刘卫东");
//            put("52303","卡尔德克");
//            put("84645","吉利奥蒂");
//            //山东鲁能
//            put("113207","刘震理");
//            put("155890","韩镕泽");
//            put("113447","王大雷");
//            put("117947","李松益");
//            put("105722","多纳希门托");
//            put("113384","王彤");
//            put("113390","郑铮");
//            put("167858","齐天羽");
//            put("210565","陈哲超");
//            put("230383","刘洋");
//            put("113451","戴琳");
//            put("222515","王炯");
//            put("113132","赵明剑");
//            put("61153","祖斯利尔");
//            put("113383","王永珀");
//            put("100657","蒙蒂略");
//            put("113437","刘彬彬");
//            put("113393","张驰");
//            put("113438","李微");
//            put("114084","吴兴涵");
//            put("37499","蒿俊闵");
//            put("113209","宋龙");
//            put("116731","金敬道");
//            put("230382","李海龙");
//            put("17784","佩莱");
//            put("28527","塔尔德利");
//            put("42758","P-西塞");
//            put("83763","杨旭");
//            put("224061","郭田雨");
//            //杭州绿城
//            put("205803","张磊");
//            put("113330","邹德海");
//            put("113318","刘洋");
//            put("180438","岳鑫");
//            put("155899","葛振");
//            put("119080","孙正傲");
//            put("113226","吴伟");
//            put("223980","陈晓");
//            put("40539","斯皮拉诺维奇");
//            put("223982","童磊");
//            put("113316","曹海清");
//            put("223983","王玚");
//            put("51240","吴范锡");
//            put("115113","臧一峰");
//            put("180526","陈柏良");
//            put("167854","罗竞");
//            put("113287","冯刚");
//            put("223981","吴伟");
//            put("156028","赵宇豪");
//            put("113298","董宇");
//            put("113351","黄希扬");
//            put("87287","程谋义");
//            put("113280","陈中流");
//            put("180454","宋海旺");
//            put("49802","加比奥内塔");
//            put("156025","庄佳杰");
//            put("221261","谈杨");
//            put("223984","高华泽");
//            //辽宁宏运
//            put("118372","刘伟国");
//            put("155900","张振强");
//            put("113172","石笑天");
//            put("113245","吴高俊");
//            put("113246","杨善平");
//            put("113286","郑涛");
//            put("113320","宋琛");
//            put("223993","张天龙");
//            put("48764","阿萨尼");
//            put("27570","王亮");
//            put("113253","芦强");
//            put("20309","泽威特");
//            put("155981","孙世林");
//            put("113247","杨宇");
//            put("113322","张野");
//            put("30951","肇俊哲");
//            put("113161","王亮");
//            put("113144","倪玉崧");
//            put("113525","张靖洋");
//            put("113248","金泰延");
//            put("223994","王峤");
//            put("113296","胡延强");
//            put("155988","王珐");
//            put("155920","刘尚坤");
//            put("37387","詹姆斯");
//            put("113307","王皓");
//            put("224059","冯伯元");
//            put("111534","图雷");
//            put("203224","雷永驰");
//            //石家庄永昌
//            put("113444","邵镤亮");
//            put("81796","关震");
//            put("113141","姜积弘");
//            put("82359","赵容亨");
//            put("113167","许博");
//            put("113258","李凯");
//            put("113075","吕建军");
//            put("167856","糜昊伦");
//            put("113243","李春郁");
//            put("113061","毛剑卿");
//            put("83762","崔鹏");
//            put("52698","鲁本");
//            put("113182","高增翔");
//            put("113105","孙昊晟");
//            put("180483","王军辉");
//            put("215839","郭松");
//            put("113062","李提香");
//            put("76156","隆东");
//            put("40367","门迪");
//            put("100158","毛里西奥");
//            put("38586","马修斯");
//            //长春亚泰
//            put("155902","吴亚轲");
//            put("208332","祎凡");
//            put("113505","宋振瑜");
//            put("167100","积施利");
//            put("155891","阎世鹏");
//            put("79027","伊斯梅洛夫");
//            put("223944","殷亚吉");
//            put("180406","邵帅");
//            put("113078","孙捷");
//            put("113290","范晓冬");
//            put("223945","左伊滕");
//            put("113089","车凯");
//            put("113093","裴帅");
//            put("86537","马季奇");
//            put("54069","格里乌斯");
//            put("113079","蒋哲");
//            put("27583","杜震宇");
//            put("180441","何超");
//            put("113087","张笑飞");
//            put("83761","阎峰");
//            put("113092","李光");
//            put("155913","刘奇鸣");
//            put("180413","韩德明");
//            put("112642","奥泽戈维奇");
//            put("48824","M-莫雷诺");
//            put("155911","杨贺");
//            put("113085","李尚");
//            put("113080","程长城");
//
//        }
//    };

    public static String urlBuilder(String id){

        String url = "http://match.sports.sina.com.cn/football/player_iframe.php?id=" + id + "&season=2016&dpc=1";
        return url;
    }

    public static String urlBioBuilder(String id){

        String url = "http://match.sports.sina.com.cn/football/player.php?id="+id+"&dpc=1";
        return url;
    }


    public static String getPreferredPlayer(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.pref_name_key),
                context.getString(R.string.pref_player_default));
    }

    public static int getFlagForNation(String nation){

        switch (nation){

            case "中国":
                return R.drawable.flag_china;

            case "中华台北":
                return R.drawable.flag_taiwan;

            case "中国香港":
                return R.drawable.flag_hongkong;

            case "乌兹别克":
                return R.drawable.flag_uzbekistan;

            case "菲律宾":
                return R.drawable.flag_philippines;

            case "法国":
                return R.drawable.flag_france;

            case "意大利":
                return R.drawable.flag_italy;

            case "葡萄牙":
                return R.drawable.flag_portugal;

            case "以色列":
                return R.drawable.flag_israel;

            case "塞尔维亚":
                return R.drawable.flag_serbia;

            case "波黑":
                return R.drawable.flag_boznia;

            case "瑞典":
                return R.drawable.flag_sweden;

            case "丹麦":
                return R.drawable.flag_denmark;

            case "韩国":
                return R.drawable.flag_southkorea;

            case "朝鲜":
                return R.drawable.flag_northkorea;

            case "克罗地亚":
                return R.drawable.flag_crotia;

            case "斯洛文尼亚":
                return R.drawable.flag_slovenia;

            case "巴西":
                return R.drawable.flag_brazil;

            case "阿根廷":
                return R.drawable.flag_argentina;

            case "Venezuela":
                return R.drawable.flag_venezuela;

            case "委内瑞拉":
                return R.drawable.flag_venezuela;

            case "玻利维亚":
                return R.drawable.flag_bolivia;

            case "智利":
                return R.drawable.flag_chile;

            case "Senegal":
                return R.drawable.flag_senegal;

            case "塞内加尔":
                return R.drawable.flag_senegal;

            case "刚果":
                return R.drawable.flag_congodr;

            case "Congo DR":
                return R.drawable.flag_congodr;

            case "Gambia":
                return R.drawable.flag_gambia;

            case "赞比亚":
                return R.drawable.flag_gambia;

            case "Mozambique":
                return R.drawable.flag_mozambique;

            case "莫桑比克":
                return R.drawable.flag_mozambique;

            case "Gabon":
                return R.drawable.flag_gabon;

            case "加蓬":
                return R.drawable.flag_gabon;

            case "喀麦隆":
                return R.drawable.flag_cameroon;

            case "科特迪瓦":
                return R.drawable.flag_ivorycoast;

            case "哥伦比亚":
                return R.drawable.flag_columbia;

            case "尼日利亚":
                return R.drawable.flag_nigeria;

            case "澳大利亚":
                return R.drawable.flag_australia;

            case "土耳其":
                return R.drawable.flag_turkey;




            default:return R.drawable.flag_default;

        }
    }

    public static int getImageForMatchType(String game){

        switch (game){

            case "中超":
                return R.drawable.csl;

            case "亚冠":
                return R.drawable.afc;

            default:return R.drawable.match_default;

        }
    }

    public static int getBadgeForTeam(String team){

        switch (team){

            case "北京国安":
                return R.drawable.badge_beijing_guoan;

            case "重庆力帆":
                return R.drawable.badge_chongqing_lifan;

            case "上海上港":
                return R.drawable.badge_shanghai_shanggang;

            case "长春亚泰":
                return R.drawable.badge_changchun_yatai;

            case "广州富力":
                return R.drawable.badge_guangzhou_fuli;

            case "广州恒大":
                return R.drawable.badge_guangzhou_hengda;

            case "杭州绿城":
                return R.drawable.badge_hangzhou_lvcheng;

            case "河北华夏":
                return R.drawable.badge_heibei_huaxia;

            case "河南建业":
                return R.drawable.badge_henan_jianye;

            case "天津泰达":
                return R.drawable.badge_tianjin_taida;

            case "辽宁宏运":
                return R.drawable.badge_liaoning_hongyun;

            case "石家庄永昌":
                return R.drawable.badge_shijiazhuang_yongchang;

            case "山东鲁能":
                return R.drawable.badge_shandong_luneng;

            case "延边富德":
                return R.drawable.badge_yanbian_fude;

            case "江苏苏宁":
                return R.drawable.badge_jiangsu_suning;

            case "上海申花":
                return R.drawable.badge_shanghai_shenhua;

            case "全北现代":
                return R.drawable.badge_Jeonbuk;

            case "东京FC":
                return R.drawable.badge_fctokyo;

            case "越南平阳":
                return R.drawable.badge_becamex;

            case "首尔FC":
                return R.drawable.badge_fcseoul;

            case "广岛三箭":
                return R.drawable.badge_sanfrecce;

            case "武里南联":
                return R.drawable.badge_buriram;

            case "墨尔本胜利":
                return R.drawable.badge_melbourne;

            case "水原三星":
                return R.drawable.badge_suwon;

            case "大阪钢巴":
                return R.drawable.badge_gamba;

            case "悉尼FC":
                return R.drawable.badge_sydney;

            case "浦和红钻":
                return R.drawable.badge_urawareddiamond;

            case "浦项制铁":
                return R.drawable.badge_pohang;

            default:return R.drawable.badge_default;
        }
    }

    public static int getImageForPlayer(String playerId){

        switch (playerId){

            case "61034":
                return R.drawable.icon_ralf;

            case "168101":
                return R.drawable.icon_fernando;

            case "116730":
                return R.drawable.icon_wulei;

            default:return R.drawable.icon_default;

        }
    }

    public static int getKitForTeam(String team){

        switch (team){

            case "北京国安":
                return R.drawable.kit_beijing_guoan;

            case "重庆力帆":
                return R.drawable.kit_chongqing_lifan;

            case "上海上港":
                return R.drawable.kit_shanghai_shanggang;

            case "长春亚泰":
                return R.drawable.kit_changchun_yatai;

            case "广州富力":
                return R.drawable.kit_guangzhou_fuli;

            case "广州恒大":
                return R.drawable.kit_guangzhou_hengda;

            case "杭州绿城":
                return R.drawable.kit_hangzhou_lvcheng;

            case "河北华夏":
                return R.drawable.kit_hebei_huaxia;

            case "河南建业":
                return R.drawable.kit_henan_jianye;

            case "天津泰达":
                return R.drawable.kit_tianjin_taida;

            case "辽宁宏运":
                return R.drawable.kit_liaoning_hongyun;

            case "石家庄永昌":
                return R.drawable.kit_shijiazhuang_yongchang;

            case "山东鲁能":
                return R.drawable.kit_shandong_luneng;

            case "延边富德":
                return R.drawable.kit_yanbian_fude;

            case "江苏苏宁":
                return R.drawable.kit_jiangsu_suning;

            case "上海申花":
                return R.drawable.kit_shanghai_shenhua;

            default:return R.drawable.kit_default;
        }

    }

}
