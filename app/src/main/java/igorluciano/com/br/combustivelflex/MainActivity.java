package igorluciano.com.br.combustivelflex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mopub.mobileads.MoPubView;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private MoPubView moPubView;

    EditText txtValGas,txtValEta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this);

        setContentView(R.layout.activity_main);

        moPubView = (MoPubView) findViewById(R.id.mopub_sample_ad);
        // TODO: Replace this test id with your personal ad unit id
        moPubView.setAdUnitId("c280f964aaff489eab9bb461f036b618");
        moPubView.loadAd();


        txtValGas = (EditText)findViewById(R.id.gasolina_edit_text);
        txtValEta = (EditText)findViewById(R.id.etanol_edit_text);
        txtValGas.addTextChangedListener(Mask.insert(Mask.DOUBLE_MASK, txtValGas));
        txtValEta.addTextChangedListener(Mask.insert(Mask.DOUBLE_MASK, txtValEta));

        Button btnCalc = (Button) findViewById(R.id.btnCalcular);
        btnCalc.setOnClickListener(this);

        Button btnClear = (Button) findViewById(R.id.btnLimpar);
        btnClear.setOnClickListener(this);

        AdView ads = (AdView) findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder().build();
        ads.loadAd(request);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCalcular:
                if(TextUtils.isEmpty(txtValGas.getText().toString()) || TextUtils.isEmpty(txtValEta.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                }else{
                    Intent it = new Intent(this, ResultActivity.class);
                    it.putExtra("valGas", Double.parseDouble(txtValGas.getText().toString()));
                    it.putExtra("valEta", Double.parseDouble(txtValEta.getText().toString()));
                    startActivity(it);
                }
                break;
            case R.id.btnLimpar:
                txtValGas.setText("");
                txtValEta.setText("");
                break;
        }
    }

    @Override
    protected void onDestroy(){
        moPubView.destroy();
        super.onDestroy();
    }
}
