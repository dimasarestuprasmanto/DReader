package com.dimasarp.dreader.Common;

import com.dimasarp.dreader.Model.Chapter;
import com.dimasarp.dreader.Model.Comic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Common {
    public static List<Comic> comicList = new ArrayList<>();
    public static HashSet<Comic> comicHashSet = new HashSet<>();
    public static Comic comicSelected;
    public static List<Chapter> chapterList;
    public static Chapter chapterSelected;
    public static int chapterIndex=-1;
    public static String[] categories = {
            "Action",
            "Adult",
            "Adventure",
            "Comedy",
            "Completed",
            "Cooking",
            "Doujinshi",
            "Drama",
            "Drop",
            "Ecchi",
            "Fantasy",
            "Gender bender",
            "Harem",
            "Historical",
            "Horror",
            "Jose",
            "Latest",
            "Manhua",
            "Manhwa",
            "Material arts",
            "Mature",
            "Mecha",
            "Medical",
            "Mystery",
            "Newest",
            "One shot",
            "Ongoing",
            "Psychological",
            "Romance",
            "School life"};

    public static String formatString(String name) {
        StringBuilder finalResult = new StringBuilder(name.length() > 14?name.substring(0,14)+" . . .":name);
        return finalResult.toString();
    }
}
