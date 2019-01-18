package com.example.cheeku.one_control;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static int[] string2dec(int[] irData, int frequency) {
        int formula = shouldEquationRun();

        //Should we run any computations on the irData?
        if (formula != 0) {
            for (int i = 0; i < irData.length; i++) {
                if (formula == 1) {
                    irData[i] = irData[i] * (1000000 / frequency);//the brackets should avoid an arithmetic overflow
                } else if (formula == 2) {
                    irData[i] = (int) Math.ceil(irData[i] * 26.27272727272727); //this is the samsung formula as per http://developer.samsung.com/android/technical-docs/Workaround-to-solve-issues-with-the-ConsumerIrManager-in-Android-version-lower-than-4-4-3-KitKat
                }
            }
        }
        return irData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button transmitButton = (Button) this.findViewById(R.id.transmit_button);
        transmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ConsumerIrManager mCIR;
                mCIR = (ConsumerIrManager) getSystemService(Context.CONSUMER_IR_SERVICE);
                int[] pattern_off = {8918, 4446, 572, 546, 572, 546, 572, 1638, 572, 546, 572, 546,
                        572, 546, 572, 546, 572, 546, 572, 1638, 572, 1638, 572, 546, 572, 1638, 572,
                        1638, 572, 1638, 572, 1638, 572, 1638, 572, 1638, 572, 546, 572, 1638, 572,
                        546, 572, 546, 572, 546, 572, 1638, 572, 1638, 572, 546, 572, 1638, 572, 546,
                        572, 1638, 572, 1638, 572, 1638, 572, 546, 572, 546, 572, 39598, 8892, 2210, 572, 95186};
                int[] k = string2dec(pattern_off, 38000)
                mCIR.transmit(38000, k);
            }
        });

    }

}
