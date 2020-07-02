package com.dimasarp.dreader.Interface;

import com.dimasarp.dreader.Model.Comic;

import java.util.List;

public interface IComicLoadDone {
    void onComicLoadDoneListener(List<Comic> comicList);
}
