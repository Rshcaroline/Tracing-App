package com.fdwireless.trace.infoclass;

import com.fdwireless.trace.mapmodule.R;

import java.util.ArrayList;

/**
 * Created by AuCson on 2017/2/24.
 */

public class Route {
    private static class route_zj
    {
        static boolean rls = true; // release version or not
        public static ArrayList<Clip> getRoute()
        {
            ArrayList<Clip> route = new ArrayList<>();

            route.add(new Clip(
                    Clip.AR,31.1910421919,121.5917533598,"演出厅：海艺数字影城",rls,9001+""
            ).setImg(R.drawable.ar_cinema));
            route.add(new Clip(
                    Clip.AR,31.1907751919,121.5930513598,"兔子镇：高科苑宿舍",rls,9002+""
            ).setImg(R.drawable.ar_gate));

            route.add(new Clip(
                    Clip.AR,31.1905861919,121.5919913598,"兔子的家：高科苑1号楼",rls,9003+""
            ).setImg(R.drawable.ar_dorm));
            route.add(new Clip(
                    Clip.DEST,31.1907091919,121.5919863598,"兔子的家：1号楼311",rls,9004+""). //这边剧情稍微分割了下；不过没关系，因为和AR没有关系
                    setQAD("兔子的难题：谜题的答案是？","3","还挺厉害嘛！可是……你还记得开门的暗号吗？")
            );
            route.add(new Clip(
                    Clip.AR,31.1907251919,121.5919883598,"兔子的家：1号楼311",rls,9005+""
            ).setImg(R.drawable.ar_311));

            route.add(new Clip(
                    Clip.AR,31.1892071919,121.5970213598,"有书的好地方：望道书阁",rls,9006+""
            ).setImg(R.drawable.ar_bookstore));
            route.add(new Clip(
                    Clip.AR,31.1899371919,121.5966533598,"博物馆分馆：张江图书馆",rls,9007+""
            ).setImg(R.drawable.ar_lib));
            route.add(new Clip(
                    Clip.AR,31.1916378733,121.5994984543,"软件楼最舒适的房间：102",rls,9008+""
            ).setImg(R.drawable.ar_meeting));
            route.add(new Clip(
                    Clip.AR,31.1914298733,121.6001274543,"车管所：张江校车点",rls,9009+""
            ).setImg(R.drawable.ar_station));
            route.add(new Clip(
                    Clip.AR,31.1891474563,121.5988179274,"终·1024：张江校区正门",rls,9010+""
            ).setImg(R.drawable.ar_zju));

            return route;
        }
    }

    public ArrayList<Clip> selectRoute(String s)
    {
        if(s.equals("ZJ"))
            return route_zj.getRoute();
        else
            return null;
    }
}
