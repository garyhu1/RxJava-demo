package com.se.rxjavademo.pojo;

/**
 * Created by yb on 2019/3/7.
 */

public class LocationBean {


    /**
     * code : 0
     * msg : success
     * cost : 2
     * data : {"locate_icon_gif":{"width":"50","url":"","height":"50"},"locate_icon_png":{"width":"50","url":"","height":"50"}}
     */

    private int code;
    private String msg;
    private int cost;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * locate_icon_gif : {"width":"50","url":"","height":"50"}
         * locate_icon_png : {"width":"50","url":"","height":"50"}
         */

        private LocateIconGifBean locate_icon_gif;
        private LocateIconPngBean locate_icon_png;

        public LocateIconGifBean getLocate_icon_gif() {
            return locate_icon_gif;
        }

        public void setLocate_icon_gif(LocateIconGifBean locate_icon_gif) {
            this.locate_icon_gif = locate_icon_gif;
        }

        public LocateIconPngBean getLocate_icon_png() {
            return locate_icon_png;
        }

        public void setLocate_icon_png(LocateIconPngBean locate_icon_png) {
            this.locate_icon_png = locate_icon_png;
        }

        public static class LocateIconGifBean {
            /**
             * width : 50
             * url :
             * height : 50
             */

            private String width;
            private String url;
            private String height;

            public String getWidth() {
                return width;
            }

            public void setWidth(String width) {
                this.width = width;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }
        }

        public static class LocateIconPngBean {
            /**
             * width : 50
             * url :
             * height : 50
             */

            private String width;
            private String url;
            private String height;

            public String getWidth() {
                return width;
            }

            public void setWidth(String width) {
                this.width = width;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }
        }
    }
}
