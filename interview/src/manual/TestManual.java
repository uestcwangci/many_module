package manual;

import manual.hashMap.手写hashMap;

public class TestManual {
    public static void main(String[] args) throws Exception {
        testHashMap();
    }

    private static void testHashMap() throws Exception {
        手写hashMap<String, String> map = new 手写hashMap<>();

        map.put("1", "1");
        map.put("2fd", "2");
        map.put("sdf3", "3");
        map.put("sadf4", "4");
        map.put("5df", "5");
        map.put("df6", "6");
        map.put("fsad7", "7");
        map.put("dsf8", "8");
        map.put("ds9", "9");
        map.put("1dsaf0", "10");
        map.put("1sdf1", "10");
        map.put("1gds2", "10");
        map.put("1gdsf3", "10");
        map.put("1dfgas4", "10");
        map.put("1qw5", "10");
        map.put("1qwe6", "10");
        map.put("1qd7", "10");
        map.put("1ad8", "10");
        map.put("1xzc9", "10");
        map.put("2xcv0", "10");
        map.put("2rew1", "10");
        map.put("3rew1", "10");
        map.put("4rew1", "10");
        map.put("5rew1", "10");
        System.out.println(map.getSize());


    }
}
