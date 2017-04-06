package com.nasasurvivors.water.app.waterapp;


import com.nasasurvivors.water.app.waterapp.controller.MainActivity;
import com.nasasurvivors.water.app.waterapp.model.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * TESTS for 3 methods in AppSingleton: addSourceReport (Leonie Reif), addPurityReport (Zach Schlesinger) and logout (Tommaso Pieroncini)
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class AppSingletonTest {
    private static WaterPurityReport purity1;
    private static WaterPurityReport purity2;
    private static WaterPurityReport purity3;
    private static WaterPurityReport purity4;
    private static WaterSourceReport source1;
    private static WaterSourceReport source2;
    private static WaterSourceReport source3;
    private static WaterSourceReport source4;
    private static ArrayList<WaterPurityReport> pReports;
    private static ArrayList<WaterSourceReport> sReports;

    @Before
    public void setUp() {
        pReports = new ArrayList<WaterPurityReport>();
        sReports = new ArrayList<WaterSourceReport>();
        purity1 = new WaterPurityReport(new Date(), "Username", new LatLng(1.0, 1.0), WaterSafetyCondition.SAFE, 30, 50, 2);
        purity2 = new WaterPurityReport(new Date(),
                "OtherUsername", new LatLng(4.0, 1.0), WaterSafetyCondition.UNSAFE, 40, 500, 7);
        purity3 = new WaterPurityReport();
        purity4 = new WaterPurityReport(new Date(), "UserName",
                new LatLng(1.0, 1.0), WaterSafetyCondition.TREATABLE, 20, 100, 2);
        source1 = new WaterSourceReport(new Date(), new String("Username"),
                new LatLng(1.0, 2.0), WaterType.BOTTLED, WaterCondition.POTABLE, 4);
        source2 = new WaterSourceReport(new Date(), new String(""),
                new LatLng(6.0, 2.0), WaterType.SPRING, WaterCondition.TREATABLEMUDDY, 46);
        source3 = new WaterSourceReport();
        source4 = new WaterSourceReport(new Date(),
                new String("Username"), new LatLng(6.0, 2.0), WaterType.LAKE, WaterCondition.TREATABLECLEAR, 6);
        pReports = new ArrayList<WaterPurityReport>();
        pReports.add(purity1);
        pReports.add(purity2);
        pReports.add(purity3);
        pReports.add(purity4);
        sReports.add(source1);
        sReports.add(source2);
        sReports.add(source3);
        sReports.add(source4);
    }
    @Test
    public void testAddPurityReport() {
        AppSingleton.getInstance().addPurityReport(purity1);
        AppSingleton.getInstance().addPurityReport(purity2);
        AppSingleton.getInstance().addPurityReport(purity3);
        AppSingleton.getInstance().addPurityReport(purity4);

        // Testing that the arraylists are equal once they have the same reports stored
        Assert.assertEquals(pReports, AppSingleton.getInstance().getPurityReports());
        WaterPurityReport[] t2 = new WaterPurityReport[AppSingleton.getInstance().getPurityReports().size()];
        WaterPurityReport[] t1 = new WaterPurityReport[pReports.size()];
        Assert.assertArrayEquals(pReports.toArray(t1), AppSingleton.getInstance().getPurityReports().toArray(t2));
    }

    @Test
    public void testAddSourceReport() {
        AppSingleton.getInstance().addSourceReport(source1);
        AppSingleton.getInstance().addSourceReport(source2);
        AppSingleton.getInstance().addSourceReport(source3);
        AppSingleton.getInstance().addSourceReport(source4);

        // testing that reports have been added to the arraylist correctly
        Assert.assertEquals(sReports, AppSingleton.getInstance().getSourceReports());
        WaterSourceReport[] t2 = new WaterSourceReport[AppSingleton.getInstance().getSourceReports().size()];
        WaterSourceReport[] t1 = new WaterSourceReport[sReports.size()];
        Assert.assertArrayEquals(sReports.toArray(t1), AppSingleton.getInstance().getSourceReports().toArray(t2));
    }

    @Test
    public void testLogOut() {
        AppSingleton.getInstance().addSourceReport(source1);
        AppSingleton.getInstance().addSourceReport(source2);
        AppSingleton.getInstance().addSourceReport(source3);
        AppSingleton.getInstance().addSourceReport(source4);
        AppSingleton.getInstance().addPurityReport(purity1);
        AppSingleton.getInstance().addPurityReport(purity2);
        AppSingleton.getInstance().addPurityReport(purity3);
        AppSingleton.getInstance().addPurityReport(purity4);
        Assert.assertEquals(AppSingleton.logout(), true);

        // Testing that logout happened correctly and all information has been cleared
        Assert.assertEquals(AppSingleton.getInstance().getLoggedOut(), true);
        Assert.assertEquals(AppSingleton.getInstance().getCurrentUser(), null);
        Assert.assertEquals(AppSingleton.getLocation(), null);
        Assert.assertEquals(AppSingleton.getInstance().getSourceReports().size(), 0);
        Assert.assertEquals(AppSingleton.getInstance().getPurityReports().size(), 0);
    }
}