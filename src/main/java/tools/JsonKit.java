package tools;

public class JsonKit {
    private JsonKit() {};
    static String json_dir = "books/book_json";
    public static String getJson(String book_name) {
        String json_path = json_dir + "/" + book_name + ".json";
        return FileKit.readFile(json_path);
    }

    public static String readFile(String path){
        return "";
    }

}
