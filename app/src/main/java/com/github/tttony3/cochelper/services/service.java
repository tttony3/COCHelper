package com.github.tttony3.cochelper.services;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Environment;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static java.lang.Math.abs;


/**
 * Created by tangli on 2017/1/9.
 * Website: https://github.com/tttony3
 */

public class service extends AccessibilityService {
    int jiantou = 1;

    int width = 1920;
    ArrayList<Point> pointsJin = new ArrayList<>();
    ArrayList<Point> pointsShui = new ArrayList<>();
    ArrayList<Point> pointsHShui = new ArrayList<>();
    ArrayList<Point> pointsZiyuan = new ArrayList<>();
    ArrayList<Point> pointsZengyuan = new ArrayList<>();
    String TAG = "cocservice";
    String path = Environment.getExternalStorageDirectory().getPath() + "/cochelper";
    String resultPath = Environment.getExternalStorageDirectory().getPath() + "/result";
    long time = 0;
    int countOf32 = 0;
    Thread myThread;
    private boolean cocIsRunning = false;
    //矿(545,327), 水(451,664),黑水(1014,899)(26*26) 箭头R(15,443) L(744,441)(33*58) 增援(562,412)(50*30)
    //26*26
    int[] jinkuang = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[] shui = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0};
    int[] heishui = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    //33*58
    int[] jiantouR = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[] jiantouL = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    //50*30
    int[] zy = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,1,0,0,0,1,0,1,0,0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,1,0,1,0,1,1,0,0,0,0,0,1,1,0,0,0,0,0,1,0,1,1,0,0,1,0,0,0,0,0,0,0,0,1,1,1,0,1,0,0,0,0,0,0,0,1,0,1,1,0,0,1,0,0,0,0,1,0,0,0,1,0,1,0,0,1,0,0,1,0,0,1,0,0,0,0,1,1,0,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,1,1,0,0,1,1,1,0,0,1,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,1,0,0,1,0,0,0,1,1,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,1,1,1,1,1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,1,0,1,0,0,0,0,0,1,0,1,0,0,1,0,1,0,0,0,0,0,0,0,0,1,0,0,0,1,0,1,0,1,0,1,0,1,1,1,0,0,0,0,0,1,0,0,1,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,1,1,0,1,0,0,0,1,0,0,1,0,0,0,0,0,0,0,1,0,1,0,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,1,0,1,0,1,0,1,0,1,0,0,1,0,0,1,0,0,1,0,1,0,0,0,0,1,0,0,1,1,0,0,0,0,1,0,0,0,0,1,0,1,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,1,0,1,0,0,1,1,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,1,1,1,0,1,1,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,1,0,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,1,1,0,0,0,0,1,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,1,1,0,0,0,1,1,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,1,0,0,0,1,0,0,1,0,0,1,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,1,0,0,0,1,1,0,1,1,0,1,1,1,0,0,0,0,0,0,0,0,0,1,0,0,1,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,1,0,0,0,1,0,1,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,1,0,1,1,0,0,0,1,0,0,0,0,1,1,1,1,1,1,1,1,1,0,0,1,0,0,0,1,1,0,0,0,0,0,1,0,0,1,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,1,0,0,0,0,0,1,0,1,0,0,1,0,0,0,0,1,0,0,1,0,1,0,1,0,1,1,1,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0,1,0,1,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,1,0,0,1,0,0,1,1,0,0,1,0,0,1,0,0,1,1,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,1,0,1,0,1,0,0,0,0,0,1,0,1,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,1,1,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,1,0,0,0,0,1,1,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,1,1,0,0,0,0,0,0,1,0,0,0,0,0,1,1,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,1,0,1,1,1,1,1,0,0,0,1,1,1,1,1,1,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    //字样220/220(201,140)(142*32)
    private boolean isCanAtt = false;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {

        Log.d(TAG, "onAccessibilityEvent:" + accessibilityEvent.getEventType());
        if (accessibilityEvent.getEventType() == 2048) {

            if (countOf32 >= 5) {
                cocIsRunning = false;
            }
        } else if (accessibilityEvent.getEventType() == 32) {
            if (++countOf32 == 4) {
                cocIsRunning = true;
                myThread.start();
            }
        }
    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "onInterrupt");
    }

    @Override
    public void onCreate() {
        String apkRoot = "chmod 777 " + getPackageCodePath();
        execLinuxCmd(apkRoot);
        myThread = new MyThread();


        super.onCreate();
    }

    public void execLinuxCmd(String cmd) {
        Process process = null;
        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                    process.destroy();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    class MyThread extends Thread {

        @Override

        public void run() {
            Log.d(TAG, "MyThread run");
            try {
                sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (cocIsRunning) {

                File f = new File(path);
                if(!f.exists()){
                    f.mkdir();
                }
                for (File tmp : f.listFiles())
                    tmp.delete();
                time = System.currentTimeMillis();
                try {
                    Thread.sleep(1000);
                    execLinuxCmd("screencap -p " + path + "/screen.png");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!cocIsRunning)
                    break;

                pointsJin.clear();
                pointsShui.clear();
                pointsHShui.clear();
                pointsZiyuan.clear();


                processBitmap();

                if (!cocIsRunning)
                    break;
                pointsZiyuan.addAll(pointsJin);
                pointsZiyuan.addAll(pointsShui);
                pointsZiyuan.addAll(pointsHShui);
                for(int i =0;i<pointsZiyuan.size();i++)
                    for(int j=0;j<lastClickZiyuan.size();j++){
                        if(pointsZiyuan.get(i).x ==lastClickZiyuan.get(j).x &&
                                pointsZiyuan.get(i).y ==lastClickZiyuan.get(j).y){
                            pointsZiyuan.remove(i);
                            i--;
                        }
                    }

                if (jiantou ==1 && pointsZiyuan.size() > 0) {
                    if (!cocIsRunning)
                        break;
                    clickZiyuan(pointsZiyuan);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else if(jiantou ==1 && pointsZiyuan.size() == 0){
                    if (!cocIsRunning)
                        break;
                    clickOnce(25,460); //点击右箭头打开聊天界面

                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else if(jiantou ==2 && pointsZengyuan.size()>0){
                    for(Point p:pointsZengyuan){
                        if (!cocIsRunning)
                            break;
                        clickZengyuan(p);

                    }
                    pointsZengyuan.clear();
                    if (!cocIsRunning)
                        break;
                    //增援结束，点击左箭头
                    ArrayList<Point> points = new ArrayList<>();
                    Point p1 = new Point();
                    Point p2 = new Point();
                    Point p3 = new Point();
                    Point p4 = new Point();
                    Point p5 = new Point();
                    p1.set(743,464);//左箭头
                    p2.set(103,753);//兵营
                    p3.set(1530,72);//一键训练
                    p4.set(1708,521);//训练
                    p5.set(295,67);//概况界面
                    points.add(p1);
                    points.add(p2);
                    points.add(p3);
                    points.add(p4);
                    points.add(p5);
                    clickList(points);
                }


            }

            try {
                sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countOf32 = 0;
            Log.d(TAG, "MyThread finish ");
            super. run();
        }

        private void clickList(ArrayList<Point> points) {
            Log.d(TAG, "clickList " );
            GestureDescription.Builder builder = new GestureDescription.Builder();
            int i = 0;
            for(Point p :points){
                i++;
                Path path = new Path();
                path.moveTo(p.x, p.y);
                builder.addStroke(new GestureDescription.StrokeDescription(path, 500*i, 100));
            }

            GestureDescription gestureDescription = builder.build();
            dispatchGesture(gestureDescription, new GestureResultCallback() {
                @Override
                public void onCompleted(GestureDescription gestureDescription) {
                    Log.d(TAG, "clickList onCompleted");
                    super.onCompleted(gestureDescription);
                }

                @Override
                public void onCancelled(GestureDescription gestureDescription) {
                    super.onCancelled(gestureDescription);
                }
            }, null);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        private void clickOnce(int x ,int y){
            Log.d(TAG, "clickOnce : " + x+" "+y);
            GestureDescription.Builder builder = new GestureDescription.Builder();
            Path path = new Path();
            path.moveTo(x, y);
            builder.addStroke(new GestureDescription.StrokeDescription(path, 100, 100));
            GestureDescription gestureDescription = builder.build();
            dispatchGesture(gestureDescription, new GestureResultCallback() {
                @Override
                public void onCompleted(GestureDescription gestureDescription) {
                    Log.d(TAG, "onCompleted");
                    super.onCompleted(gestureDescription);
                }

                @Override
                public void onCancelled(GestureDescription gestureDescription) {
                    super.onCancelled(gestureDescription);
                }
            }, null);
        }
        private void clickZengyuan(Point p){
            Log.d(TAG, "clickZengyuan : " + p.x+" "+p.y);
            GestureDescription.Builder builder = new GestureDescription.Builder();
            Path path = new Path();
            path.moveTo(p.x, p.y);


            Path path2 = new Path();
            path2.moveTo(1768, 436);
            builder.addStroke(new GestureDescription.StrokeDescription(path, 100, 100));
           // builder.addStroke(new GestureDescription.StrokeDescription(path1, 300, 2500));
            builder.addStroke(new GestureDescription.StrokeDescription(path2, 3100, 100));
            GestureDescription gestureDescription = builder.build();
            dispatchGesture(gestureDescription, new GestureResultCallback() {
                @Override
                public void onCompleted(GestureDescription gestureDescription) {
                    Log.d(TAG, "onCompleted");
                    super.onCompleted(gestureDescription);
                }

                @Override
                public void onCancelled(GestureDescription gestureDescription) {
                    super.onCancelled(gestureDescription);
                }
            }, null);
            try {
                Thread.sleep(4300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ArrayList<Point> lastClickZiyuan = new ArrayList<>();
        long lastClickTime = 0;
        private void clickZiyuan(ArrayList<Point> points) {
            if (points.size() > 0) {
                Log.d(TAG, "click : " + points);
                if(System.currentTimeMillis()-lastClickTime>60*1000*5){
                    lastClickTime=System.currentTimeMillis();
                    lastClickZiyuan.clear();
                }
                GestureDescription.Builder builder = new GestureDescription.Builder();
                int maxCount = GestureDescription.getMaxStrokeCount();

                for (Point p : points) {
                    boolean isNew = true;
                    if (maxCount <= 1)
                        break;
                    for(Point tmp :lastClickZiyuan ){
                        if(tmp.x == p.x &&tmp.y==p.y)
                            isNew = false;
                    }
                    if(!isNew)
                        continue;
                    lastClickZiyuan.add(p);
                    Path path = new Path();
                    path.moveTo(p.x, p.y + 45);
                    GestureDescription.getMaxStrokeCount();
                    builder.addStroke(new GestureDescription.StrokeDescription(path, 200, 80));
                    maxCount--;
                }
                Path path = new Path();
                path.moveTo(1384, 116);
                GestureDescription.getMaxStrokeCount();
                builder.addStroke(new GestureDescription.StrokeDescription(path, 300, 80));
                GestureDescription gestureDescription = builder.build();
                dispatchGesture(gestureDescription, new GestureResultCallback() {
                    @Override
                    public void onCompleted(GestureDescription gestureDescription) {
                        Log.d(TAG, "onCompleted");
                        super.onCompleted(gestureDescription);
                    }

                    @Override
                    public void onCancelled(GestureDescription gestureDescription) {
                        super.onCancelled(gestureDescription);
                    }
                }, null);
            }
        }


        private void processBitmap() {

            File file = new File(path);
            File f = new File(path);
            File[] files = f.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (i == 0)
                    file = files[0];
                else {
                    if (files[i].lastModified() > file.lastModified())
                        file = files[i];
                }
            }
            if (file.getPath().equals(path))
                return;
            Bitmap bmp = BitmapFactory.decodeFile(file.getPath());
            if (bmp == null)
                return;
            int[] pixs = new int[1920 * 1080];
            int[] resultPixs = new int[1920 * 1080];
            int[] tidu = new int[1920 * 1080];
            bmp.getPixels(pixs, 0, 1920, 0, 0, 1920, 1080);
            long sum = 0;
            for (int i = 0; i < 1920 * 1080; i++) {
                pixs[i] = getHuidu((pixs[i] & 0x00ff0000) >> 16, (pixs[i] & 0x0000ff00) >> 8, pixs[i] & 0x000000ff);
                //  sum += pixs[i];
            }

//        int scale = 4;
//        double cutoff = scale*sum/(1920*1080);
//        double thresh = Math.sqrt(cutoff);

            for (int x = 1; x < 1919; x++) {
                for (int y = 1; y < 1079; y++) {
                    //Gx = [f(x + 1, y - 1) + 2f(x + 1,y) + f(x + 1,y + 1)] - [f( x- 1,y - 1) + 2f(x - 1,y) + f(x - 1,y + 1)]
                    //Gy = [f(x - 1, y - 1) + 2f(x, y - 1) + f(x + 1, y - 1)] - [f(x - 1, y + 1) + 2f(x, y + 1) + f(x + 1, y + 1)]
                    int gX = (pixs[(y - 1) * width + (x + 1)])
                            + 2 * (pixs[(y) * width + (x + 1)])
                            + (pixs[(y + 1) * width + (x + 1)])
                            - (pixs[(y - 1) * width + (x - 1)])
                            - 2 * (pixs[(y) * width + (x - 1)])
                            - (pixs[(y + 1) * width + (x - 1)]);
                    int gY = (pixs[(y - 1) * width + (x - 1)])
                            + 2 * (pixs[(y - 1) * width + (x)])
                            + (pixs[(y - 1) * width + (x + 1)])
                            - (pixs[(y + 1) * width + (x - 1)])
                            - 2 * (pixs[(y + 1) * width + (x)])
                            - (pixs[(y + 1) * width + (x + 1)]);
                    tidu[y * width + x] = abs(gX) + abs(gY);

                    if (abs(gX) + abs(gY) > 150) {
                        resultPixs[y * width + x] = 0xffffff;
                    } else {
                        resultPixs[y * width + x] = 0x000000;
                    }
                }
            }
            for (int x = 1; x < 1919; x++) {
                for (int y = 1; y < 1079; y++) {
                    if (resultPixs[y * width + x] == 0xffffff) {
                        int gX = (pixs[(y - 1) * width + (x + 1)])
                                + 2 * (pixs[(y) * width + (x + 1)])
                                + (pixs[(y + 1) * width + (x + 1)])
                                - (pixs[(y - 1) * width + (x - 1)])
                                - 2 * (pixs[(y) * width + (x - 1)])
                                - (pixs[(y + 1) * width + (x - 1)]);
                        int gY = (pixs[(y - 1) * width + (x - 1)])
                                + 2 * (pixs[(y - 1) * width + (x)])
                                + (pixs[(y - 1) * width + (x + 1)])
                                - (pixs[(y + 1) * width + (x - 1)])
                                - 2 * (pixs[(y + 1) * width + (x)])
                                - (pixs[(y + 1) * width + (x + 1)]);
                        if (gX > gY && tidu[x + (y - 1) * width] < tidu[x + y * width] && tidu[x + (y + 1) * width] < tidu[x + y * width]
                                || gX < gY && tidu[x - 1 + y * width] < tidu[x + y * width] && tidu[x + 1 + y * width] < tidu[x + y * width]) {

                        } else {
                            resultPixs[y * width + x] = 0x000000;
                        }
                    }

                }
            }
             jiantou = checkJiantou(resultPixs);
            Log.d(TAG,"jiantou:"+jiantou);
            if (jiantou == 0) {
                int p = checkPage(resultPixs);
                if(p ==1)
                   checkIsFull(resultPixs);

            } else if (jiantou == 1) {
                checkZiYuan(resultPixs);
            } else if (jiantou == 2) {
                checkZengYuan(resultPixs);
            }


//矿(545,327), 水(451,664),黑水(1014,899)(26*26) 箭头R(15,443) L(744,441)(33*58) 增援(562,412)(50*30) 字样220/220(201,140)(142*32) 军队概况(227,51)(120*58)
//            int[] temps=new int[120*58];
//            for(int x=227;x<347;x++) {
//            for (int y = 51; y < 109; y++) {
//                temps[(x-227)+(y-51)*120]=0;
//                if(resultPixs[y*width+x]==0xffffff) {
//                    temps[(x-227)+(y-51)*120]=1;
//                }
//            }
//        }
//        String s = "L {";
//        for(int i=0;i<120*58;i++){
//
//
//                if(temps[i]==1){
//                    s +="1,";
//                }else{
//                    s +="0,";
//                }
//        }
//        s +="}";
//      //  Log.d(TAG,s);
//        File f1 = new File(resultPath+"/1.txt");
//            if(!f1.exists())
//                try {
//                    f1.createNewFile();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            try {
//                FileOutputStream fop = new FileOutputStream(f1);
//                fop.write(s.getBytes());
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

//            Bitmap b = Bitmap.createBitmap(resultPixs, 0, 1920, 1920, 1080, Bitmap.Config.RGB_565);
//            try {
//                saveMyBitmap(b);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            Log.d(TAG, "done");

        }

        /**
         *
         * @param resultPixs
         * @return  0:unknow 1:军队概况
         */
        private int checkPage(int[] resultPixs) {
           // 军队概况(227,51)(120*58)
            int []gaikuang =new int[120*58];
            InputStream is;
            try {
                is = getAssets().open("gaikuang.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String s = reader.readLine();
                String [] ss = s.split(",");
                for(int i=0;i<ss.length;i++){
                    gaikuang[i]=Integer.parseInt(ss[i]);
                }
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (int y = 49; y < 53; y++)
                for (int x = 225; x < 229; x++) {
                    int delta = 0;
                    boolean b = true;
                    for (int i = 0; i < 58; i++) {
                        int lineDelta = 0;
                        for (int j = 0; j < 120; j++) {
                            if (resultPixs[(y + i) * width + x + j] == 0xffffff && gaikuang[i * 120 + j] == 0 ||
                                    resultPixs[(y + i) * width + x + j] == 0x000000 && gaikuang[i * 120 + j] == 1) {
                                lineDelta++;
                                delta++;
                            }
                        }
                        if (lineDelta > 3) {
                            b = false;
                            break;
                        }
                    }

                    if (delta < 5 && b) {
                            Log.d(TAG,"page : " +1);
                            return 1;
                    }

                }
            Log.d(TAG,"page : " +0);
                return 0;
        }

        private void checkIsFull(int[] resultPixs) {
            //字样220/220(201,140)(142*32)
            int []shuliang =new int[142*32];
            InputStream is;
            try {
                is = getAssets().open("shuliang.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String s = reader.readLine();
                String [] ss = s.split(",");
                for(int i=0;i<ss.length;i++){
                    shuliang[i]=Integer.parseInt(ss[i]);
                }
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
                boolean isChange = false;
                for (int y = 138; y < 142; y++)
                    for (int x = 197; x < 203; x++) {
                        int delta = 0;
                        boolean b = true;
                        for (int i = 0; i < 32; i++) {
                            int lineDelta = 0;
                            for (int j = 0; j < 142; j++) {
                                if (resultPixs[(y + i) * width + x + j] == 0xffffff && shuliang[i * 142 + j] == 0 ||
                                        resultPixs[(y + i) * width + x + j] == 0x000000 && shuliang[i * 142 + j] == 1) {
                                    lineDelta++;
                                    delta++;
                                }
                            }
                            if (lineDelta > 2) {
                                b = false;
                                break;
                            }
                        }

                        if (delta < 3 && b) {

                            isChange = true;
                        }

                    }
               if(isChange==true ){
                   isCanAtt = true;
               }else if (isChange = false){
                isCanAtt = false;
            }
                Log.d(TAG,"ifFull "+isChange);
        }

        private void checkZengYuan(int[] resultPixs) {
            pointsZengyuan.clear();
            //(560,170)-(580,940)
            for (int y = 170; y < 940; y++) {
                for (int x = 560; x < 580; x++) {
                    int delta = 0;
                    boolean b = true;
                    for (int i = 0; i < 30; i++) {
                        int lineDelta = 0;
                        for (int j = 0; j < 50; j++) {
                            if ((resultPixs[(y + i) * width + x + j] == 0xffffff && zy[i * 50 + j] == 0 ||
                                    resultPixs[(y + i) * width + x + j] == 0x000000 && zy[i * 50 + j] == 1) &&
                                    (resultPixs[(y + i) * width + x + j + 1] == 0xffffff && zy[i * 50 + j] == 0 ||
                                            resultPixs[(y + i) * width + x + j + 1] == 0x000000 && zy[i * 50 + j] == 1) &&
                                    (resultPixs[(y + i) * width + x + j - 1] == 0xffffff && zy[i * 50 + j] == 0 ||
                                            resultPixs[(y + i) * width + x + j - 1] == 0x000000 && zy[i * 50 + j] == 1)) {
                                lineDelta++;
                                delta++;
                            }
                        }
                        if (lineDelta > 25) {
                            b = false;
                            break;
                        }
                    }

                    if (delta < 200 && b) {
                        Point p = new Point();
                        if (pointsZengyuan.size() > 0) {
                            Point tmp = pointsZengyuan.get(pointsZengyuan.size() - 1);
                            if (abs(tmp.y - y) > 25) {
                                p.set(x, y);
                                pointsZengyuan.add(p);
                            }
                        } else {
                            p.set(x, y);
                            pointsZengyuan.add(p);
                        }
                    }

                }


            }
            Log.d(TAG,"pointsZengyuan.size() "+pointsZengyuan.size());
        }
    }

    private boolean checkOutOfBound(int x, int y) {
        if ((y < -0.73913 * x + 680) || (y > 0.746835 * x + 490) || (y < 0.673366 * x - 622) || (y > -0.765822 * x + 1945))
            return true;
        else
            return false;
    }

    int getHuidu(int r, int g, int b) {
        return (r * 19595 + g * 38469 + b * 7472) >> 16;
    }

    public void saveMyBitmap(Bitmap bitmap) throws IOException {
        File folder = new File(resultPath);
        if (!folder.exists()) {
            folder.mkdir();
        }
        File f = new File(resultPath + "/sobel.png");
        if (f.exists())
            f.delete();
        f.createNewFile();
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkZiYuan(int[] resultPixs) {
        Log.d(TAG, System.currentTimeMillis() + "");
        for (int y = 1; y < 1080 - 27; y++) {
            for (int x = 1; x < 1920 - 27; x++) {
                if (checkOutOfBound(x, y))
                    continue;
                int deltaJin = 0;
                boolean bJin = true;
                int deltaShui = 0;
                boolean bShui = true;
                int deltaHShui = 0;
                boolean bHShui = true;
                for (int i = 0; i < 26; i++) {
                    int lineDeltaJin = 0;
                    int lineDeltaShui = 0;
                    int lineDeltaHShui = 0;
                    for (int j = 0; j < 26 && (bJin || bShui || bHShui); j++) {
                        if ((resultPixs[(y + i) * width + x + j] == 0xffffff && jinkuang[i * 26 + j] == 0 ||
                                resultPixs[(y + i) * width + x + j] == 0x000000 && jinkuang[i * 26 + j] == 1) &&
                                (resultPixs[(y + i) * width + x + j + 1] == 0xffffff && jinkuang[i * 26 + j] == 0 ||
                                        resultPixs[(y + i) * width + x + j + 1] == 0x000000 && jinkuang[i * 26 + j] == 1) &&
                                (resultPixs[(y + i) * width + x + j - 1] == 0xffffff && jinkuang[i * 26 + j] == 0 ||
                                        resultPixs[(y + i) * width + x + j - 1] == 0x000000 && jinkuang[i * 26 + j] == 1)) {
                            lineDeltaJin++;
                            deltaJin++;
                        }
                        if ((resultPixs[(y + i) * width + x + j] == 0xffffff && shui[i * 26 + j] == 0 ||
                                resultPixs[(y + i) * width + x + j] == 0x000000 && shui[i * 26 + j] == 1) &&
                                (resultPixs[(y + i) * width + x + j + 1] == 0xffffff && shui[i * 26 + j] == 0 ||
                                        resultPixs[(y + i) * width + x + j + 1] == 0x000000 && shui[i * 26 + j] == 1) &&
                                (resultPixs[(y + i) * width + x + j - 1] == 0xffffff && shui[i * 26 + j] == 0 ||
                                        resultPixs[(y + i) * width + x + j - 1] == 0x000000 && shui[i * 26 + j] == 1)) {
                            lineDeltaShui++;
                            deltaShui++;
                        }
                        if ((resultPixs[(y + i) * width + x + j] == 0xffffff && heishui[i * 26 + j] == 0 ||
                                resultPixs[(y + i) * width + x + j] == 0x000000 && heishui[i * 26 + j] == 1) &&
                                (resultPixs[(y + i) * width + x + j + 1] == 0xffffff && heishui[i * 26 + j] == 0 ||
                                        resultPixs[(y + i) * width + x + j + 1] == 0x000000 && heishui[i * 26 + j] == 1) &&
                                (resultPixs[(y + i) * width + x + j - 1] == 0xffffff && heishui[i * 26 + j] == 0 ||
                                        resultPixs[(y + i) * width + x + j - 1] == 0x000000 && heishui[i * 26 + j] == 1)) {
                            lineDeltaHShui++;
                            deltaHShui++;
                        }

                    }
                    if (lineDeltaJin > 3) {
                        bJin = false;

                    }
                    if (lineDeltaShui > 4) {
                        bShui = false;

                    }
                    if (lineDeltaHShui > 3) {
                        bHShui = false;

                    }

                }
                if (deltaJin < 27 && bJin) {

                    Point p = new Point();
                    if (pointsJin.size() > 0) {
                        Point tmp = pointsJin.get(pointsJin.size() - 1);
                        if (abs(tmp.x - x) > 4 && abs(tmp.y - y) > 4) {
                                 Log.d(TAG,"deltajin : "+x+" "+y+" "+deltaJin);
                            p.set(x, y);
                            pointsJin.add(p);
                        }
                    } else {
                           Log.d(TAG,"deltajin : "+x+" "+y+" "+deltaJin);
                        p.set(x, y);
                        pointsJin.add(p);
                    }
                }
                if (deltaShui < 23 && bShui) {

                    Point p = new Point();
                    if (pointsShui.size() > 0) {
                        Point tmp = pointsShui.get(pointsShui.size() - 1);
                        if (abs(tmp.x - x) > 4 && abs(tmp.y - y) > 4) {
                                 Log.d(TAG,"deltaShui : "+x+" "+y+" "+deltaShui);
                            p.set(x, y);
                            pointsShui.add(p);
                        }
                    } else {
                          Log.d(TAG,"deltaShui : "+x+" "+y+" "+deltaShui);
                        p.set(x, y);
                        pointsShui.add(p);
                    }
                }
                if (deltaHShui < 10 && bHShui) {

                    Point p = new Point();
                    if (pointsHShui.size() > 0) {
                        Point tmp = pointsHShui.get(pointsHShui.size() - 1);
                        if (abs(tmp.x - x) > 4 && abs(tmp.y - y) > 4) {
                                  Log.d(TAG,"deltaHShui : "+x+" "+y+" "+deltaHShui);
                            p.set(x, y);
                            pointsHShui.add(p);
                        }
                    } else {
                            Log.d(TAG,"deltaHShui : "+x+" "+y+" "+deltaHShui);
                        p.set(x, y);
                        pointsHShui.add(p);
                    }
                }

            }
        }

        Log.d(TAG, System.currentTimeMillis() + "");
    }

    /**
     * @param resultPixs
     * @return 0：没有箭头 1：箭头向右 2：箭头向左
     */
    private int checkJiantou(int[] resultPixs) {
        int delta = 0;
        for (int y = 0; y < 58; y++) {
            for (int x = 0; x < 33; x++) {
                if ((resultPixs[(y + 443) * width + x + 15] == 0xffffff && jiantouR[y * 33 + x] == 0 ||
                        resultPixs[(y + 443) * width + x + 15] == 0x000000 && jiantouR[y * 33 + x] == 1)&&
                                (resultPixs[(y + 443) * width + x + 15 + 1] == 0xffffff && jiantouR[y * 33 + x] == 0 ||
                                        resultPixs[(y + 443) * width + x + 15 + 1] == 0x000000 && jiantouR[y * 33 + x] == 1) &&
                                (resultPixs[(y + 443) * width + x + 15 - 1] == 0xffffff && jiantouR[y * 33 + x] == 0 ||
                                        resultPixs[(y + 443) * width + x + 15 - 1] == 0x000000 && jiantouR[y * 33 + x] == 1)) {
                    delta++;
                }
            }
        }


        if (delta < 5)
            return 1;
        delta = 0;
        for (int y = 0; y < 58; y++) {
            for (int x = 0; x < 33; x++) {
                if ((resultPixs[(y + 441) * width + x + 744] == 0xffffff && jiantouL[y * 33 + x] == 0 ||
                        resultPixs[(y + 441) * width + x + 744] == 0x000000 && jiantouL[y * 33 + x] == 1)&&
                (resultPixs[(y + 441) * width + x + 744 + 1] == 0xffffff && jiantouR[y * 33 + x] == 0 ||
                        resultPixs[(y + 441) * width + x + 744 + 1] == 0x000000 && jiantouR[y * 33 + x] == 1) &&
                        (resultPixs[(y + 441) * width + x + 744 - 1] == 0xffffff && jiantouR[y * 33 + x] == 0 ||
                                resultPixs[(y + 441) * width + x + 744 - 1] == 0x000000 && jiantouR[y * 33 + x] == 1)) {
                    delta++;
                }
            }
        }

        if (delta < 5)
            return 2;
        return 0;
    }
}
