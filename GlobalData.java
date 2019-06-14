package com.example.signupin;

public class GlobalData {
        public static String Myemail =  "";
        public static String MyName =  "";
        public static int PostNum;
        public static String getMyemail() {
                return Myemail;
        }

        public static void setMyemail(String myemail) {
                Myemail = myemail;
        }

        public static String getMyName() {
                return MyName;
        }

        public static void setMyName(String myName) {
                MyName = myName;
        }

        public static int getPostNum() {
                return PostNum;
        }

        public static void setPostNum(int postNum) {
                PostNum = postNum;
        }
}
