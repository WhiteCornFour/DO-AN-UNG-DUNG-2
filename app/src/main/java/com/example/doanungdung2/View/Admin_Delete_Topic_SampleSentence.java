package com.example.doanungdung2.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.doanungdung2.Controller.TopicSentenceHandler;
import com.example.doanungdung2.Model.TopicSentence;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class Admin_Delete_Topic_SampleSentence extends AppCompatActivity {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    ImageView imgBackDeleteToMainPage_CDMC, imgSearchForDelete_CDMC;
    Button btnResetForLV_CDMC, btnDeleteAllForLV_CDMC;
    ListView lvDSDelete_CDMC;
    EditText edtSearchForDelete_CDMC;

    ArrayList<TopicSentence> topicSentenceArrayList = new ArrayList<>();
    ArrayList<TopicSentence> dataOfSearch = new ArrayList<>();
    boolean[] checkedForSearch;
    boolean[] checkedStates;
    TopicSentenceHandler topicSentenceHandler;
    TopicSentence topicSentence;
    Admin_Delete_Topic_SampleSentence_CustomAdapter_LV admin_Delete_Topic_SampleSentence_CustomAdapter_LV;

    String maChuDe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete_topic_sample_sentence);

        addControl();
        topicSentenceHandler = new TopicSentenceHandler(this, DB_NAME, null, DB_VERSION);

            loadAllDataTopicSentence();


        addEvent();
    }

    private void addControl() {
        imgBackDeleteToMainPage_CDMC = findViewById(R.id.imgBackDeleteToMainPage_CDMC);
        imgSearchForDelete_CDMC = findViewById(R.id.imgSearchForDelete_CDMC);
        btnResetForLV_CDMC = findViewById(R.id.btnResetForLV_CDMC);
        btnDeleteAllForLV_CDMC = findViewById(R.id.btnDeleteAll_CDMC);
        lvDSDelete_CDMC = findViewById(R.id.lvDSDelete_CDMC);
        edtSearchForDelete_CDMC = findViewById(R.id.edtSearchForDelete_CDMC);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    private void addEvent() {
        imgBackDeleteToMainPage_CDMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnResetForLV_CDMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (maChuDe == null) {
                    loadAllDataTopicSentence();
                    edtSearchForDelete_CDMC.setText("");
                } else {
                    loadDataForSearch(maChuDe);
                    edtSearchForDelete_CDMC.setText("");
                }
            }
        });

        imgSearchForDelete_CDMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maChuDeInput = edtSearchForDelete_CDMC.getText().toString();
                dataOfSearch = topicSentenceHandler.searchTopicByNameOrCode(maChuDeInput);
                checkedForSearch = new boolean[dataOfSearch.size()];

                if (dataOfSearch.isEmpty()) {
                    Toast.makeText(Admin_Delete_Topic_SampleSentence.this, "Không tìm thấy thông tin cần tìm", Toast.LENGTH_SHORT).show();
                } else {
                    admin_Delete_Topic_SampleSentence_CustomAdapter_LV = new Admin_Delete_Topic_SampleSentence_CustomAdapter_LV(
                            Admin_Delete_Topic_SampleSentence.this,
                            R.layout.activity_admin_delete_topic_sample_sentence_custom_adapter_lv,
                            dataOfSearch,
                            checkedForSearch
                    );
                    lvDSDelete_CDMC.setAdapter(admin_Delete_Topic_SampleSentence_CustomAdapter_LV);
                }
            }
        });

        lvDSDelete_CDMC.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (topicSentenceArrayList.size() == 0) {
                    topicSentence = dataOfSearch.get(position);
                } else {
                    topicSentence = topicSentenceArrayList.get(position);
                }
                String maChuDe = topicSentence.getMaChuDeMauCau();
                createDialog(maChuDe);
                return true;
            }
        });

        btnDeleteAllForLV_CDMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean anyChecked = false;

                // Kiểm tra xem danh sách checkedForSearch có dữ liệu hay không
//                if (topicSentenceArrayList.size() != 0) {
                    for (boolean checked : checkedStates) {
                        if (checked) {
                            anyChecked = true;
                            break;
                        }
                    }
//                }
//                else {
//                    // Nếu topicSentenceArrayList trống, kiểm tra checkedForSearch
//                    for (boolean checked : checkedForSearch) {
//                        if (checked) {
//                            anyChecked = true;
//                            break;
//                        }
//                    }
//                }
                if (!anyChecked) {
                    Toast.makeText(Admin_Delete_Topic_SampleSentence.this, "Hãy chọn ít nhất một chủ đề!", Toast.LENGTH_SHORT).show();
                    return;
                }
                AlertDialog alertDialog = createAlertDialogDeleteTopic();
                alertDialog.show();
            }
        });
    }

    private void deleteSelectedTopics() {
        ArrayList<TopicSentence> topicsToDelete = new ArrayList<>();

        for (int i = 0; i < checkedStates.length; i++) {
            if (checkedStates[i]) {
               topicsToDelete.add(topicSentenceArrayList.get(i));
            }
        }

        for (TopicSentence ts : topicsToDelete) {
//            boolean rs = topicSentenceHandler.check(maChuDe);
//            if (!rs) {
                topicSentenceHandler.deleteTopicSentence(ts.getMaChuDeMauCau());
//            } else {
//                dialogThongBao(maChuDe);
//            }
        }
        loadAllDataTopicSentence();
        Toast.makeText(Admin_Delete_Topic_SampleSentence.this, "Đã xóa các chủ đề đã chọn", Toast.LENGTH_SHORT).show();
    }


    private void loadAllDataTopicSentence() {
        topicSentenceArrayList = topicSentenceHandler.loadAllDataOfTopicSentence();
        checkedStates = new boolean[topicSentenceArrayList.size()];
        admin_Delete_Topic_SampleSentence_CustomAdapter_LV = new Admin_Delete_Topic_SampleSentence_CustomAdapter_LV(
                this,
                R.layout.activity_admin_delete_topic_sample_sentence_custom_adapter_lv,
                topicSentenceArrayList,
                checkedStates);
        lvDSDelete_CDMC.setAdapter(admin_Delete_Topic_SampleSentence_CustomAdapter_LV);
    }

    void loadDataForSearch(String maChuDe) {
        dataOfSearch = topicSentenceHandler.searchTopicByNameOrCode(maChuDe);

        if (dataOfSearch.size() == 0) {
            Toast.makeText(Admin_Delete_Topic_SampleSentence.this,
                    "Không có kết quả phù hợp!", Toast.LENGTH_SHORT).show();
            return;
        }

        checkedForSearch = new boolean[dataOfSearch.size()];
        admin_Delete_Topic_SampleSentence_CustomAdapter_LV = new Admin_Delete_Topic_SampleSentence_CustomAdapter_LV(
                this,
                R.layout.activity_admin_delete_topic_sample_sentence_custom_adapter_lv,
                dataOfSearch,
                checkedForSearch);

        lvDSDelete_CDMC.setAdapter(admin_Delete_Topic_SampleSentence_CustomAdapter_LV);
    }



    void createDialog(String maChuDe) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa chủ đề");
        builder.setMessage("Bạn có chắc chắn muốn xóa chủ đề này?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                topicSentenceHandler.deleteTopicSentence(maChuDe);
                loadAllDataTopicSentence();
                Toast.makeText(Admin_Delete_Topic_SampleSentence.this, "Đã xóa chủ đề", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    void dialogThongBao(String maChuDe)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setMessage("Chủ đề hiện tại đang chứa 1 danh sách mẫu câu. \n" +
                "Vui lòng kiểm tra lại các mẫu câu liên quan đến chủ đề có mã: " + maChuDe);
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Chuyển tiếp", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Admin_Delete_Topic_SampleSentence.this, Admin_Delete_SampleSentence.class);
                intent.putExtra("maChuDe", maChuDe);
                startActivity(intent);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    AlertDialog createAlertDialogDeleteTopic() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa các chủ đề đã chọn");
        builder.setMessage("Bạn có chắc chắn muốn xóa các chủ đề đã chọn?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteSelectedTopics();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder.create();
    }
}
