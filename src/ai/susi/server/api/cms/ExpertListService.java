package ai.susi.server.api.cms;

import ai.susi.DAO;
import ai.susi.json.JsonObjectWithDefault;
import ai.susi.server.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ExpertListService extends AbstractAPIHandler implements APIHandler {


    @Override
    public BaseUserRole getMinimalBaseUserRole() { return BaseUserRole.ANONYMOUS; }

    @Override
    public JSONObject getDefaultPermissions(BaseUserRole baseUserRole) {
        return null;
    }

    @Override
    public String getAPIPath() {
        return "/cms/getExpertList.json";
    }

    @Override
    public ServiceResponse serviceImpl(Query call, HttpServletResponse response, Authorization rights, final JsonObjectWithDefault permissions) {

        String model_name = call.get("model", "general");
        File model = new File(DAO.model_watch_dir, model_name);
        String group_name = call.get("group", "knowledge");
        File group = new File(model, group_name);
        String language_name = call.get("language", "en");
        File language = new File(group, language_name);

        ArrayList<String> fileList = new ArrayList<String>();
        fileList =  listFilesForFolder(language, fileList);
        JSONArray jsArray = new JSONArray(fileList);

        return new ServiceResponse(jsArray);

    }

    ArrayList<String> listFilesForFolder(final File folder,  ArrayList<String> fileList) {

        File[] filesInFolder = folder.listFiles();
        if (filesInFolder != null) {
            for (final File fileEntry : filesInFolder) {
                if (fileEntry.isDirectory()) {
//                    System.out.println("DIR : "+fileEntry.getName());
//                    listFilesForFolder(fileEntry,fileList);
                } else {
                    System.out.println("FILE : "+fileEntry.getName());
                    fileList.add(fileEntry.getName()+"");
                }
            }
        }
        return  fileList;
    }
}
