package com.example.litepaldemo;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.litepal.LitePal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_singer)
    EditText etSinger;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.tv_context)
    TextView tvContext;
    @BindView(R.id.et_id)
    EditText etId;
    @BindView(R.id.btn_del)
    Button btnDel;
    @BindView(R.id.et_update_id)
    EditText etUpdateId;
    @BindView(R.id.et_update_name)
    EditText etUpdateName;
    @BindView(R.id.et_update_singer)
    EditText etUpdateSinger;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.btn_getDatabase)
    Button btnGetDatabase;
    @BindView(R.id.btn_delDatabase)
    Button btnDelDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        findAll();

        etUpdateId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    //查单条
                    Song song = LitePal.find(Song.class, Long.parseLong(s.toString()));
                    if (song != null) {
                        etUpdateName.setText(song.getName());
                        etUpdateSinger.setText(song.getSinger());
                    }
                }
            }
        });
    }

    /**
     * 查所有
     */
    private void findAll() {
        List<Song> allSongs = LitePal.findAll(Song.class);

        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < allSongs.size(); i++) {
            stringBuffer.append(allSongs.get(i).getId() + ":" + allSongs.get(i).getName() + "--" + allSongs.get(i).getSinger() + "\n");
        }
        tvContext.setText(stringBuffer);
    }

    /**
     * 改单条
     *
     * @param id
     */
    private void update(long id) {
        Song songUpdate = new Song();
        songUpdate.setName(etUpdateName.getText().toString());
        songUpdate.setSinger(etUpdateSinger.getText().toString());
        songUpdate.update(id);
    }

    @OnClick({R.id.btn_add, R.id.btn_del, R.id.btn_update, R.id.btn_getDatabase, R.id.btn_delDatabase})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //新增
            case R.id.btn_add:
                if (TextUtils.isEmpty(etName.getText().toString()) || TextUtils.isEmpty(etSinger.getText().toString())) {
                    Toast.makeText(this, "歌手或歌名不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    Song song = new Song();
                    song.setName(etName.getText().toString());
                    song.setSinger(etSinger.getText().toString());
                    song.save();
                    findAll();
                }
                break;
            //删除
            case R.id.btn_del:
                if (TextUtils.isEmpty(etId.getText().toString())) {
                    Toast.makeText(this, "请输入要删除的id", Toast.LENGTH_SHORT).show();
                } else {
                    //删除单条
                    LitePal.delete(Song.class, Long.parseLong(etId.getText().toString()));
                    findAll();
                }
                break;
            //修改单条数据
            case R.id.btn_update:
                if (TextUtils.isEmpty(etUpdateId.getText().toString())) {
                    Toast.makeText(this, "id不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    //修改单条
                    update(Long.parseLong(etUpdateId.getText().toString()));
                    findAll();
                }
                break;
            //创建数据库
            case R.id.btn_getDatabase:
                LitePal.getDatabase();
                Song song = new Song();
                song.setName("自由飞翔");
                song.setSinger("凤凰传奇");
                song.save();
                findAll();
                break;
            //删除数据库
            case R.id.btn_delDatabase:
                LitePal.deleteDatabase("demo");
                findAll();
                break;
        }
    }
}