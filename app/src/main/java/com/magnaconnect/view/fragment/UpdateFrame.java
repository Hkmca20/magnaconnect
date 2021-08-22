/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.magnaconnect.R;
import com.magnaconnect.utils.Cons;

import androidx.fragment.app.Fragment;

import static com.magnaconnect.utils.Utility.closeSession;

public class UpdateFrame extends Fragment implements Cons {
    public String CURRENT_FRAGMENT = UpdateFrame.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void setCurrentFragment(String fragmentTag) {
        CURRENT_FRAGMENT = fragmentTag;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_comm, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.ac_ad).setVisible(false);
        menu.findItem(R.id.ac_type_i).setVisible(false);
        menu.findItem(R.id.ac_type_t).setVisible(false);
        menu.findItem(R.id.ac_rel).setVisible(false);
        menu.findItem(R.id.ac_rel_ed).setVisible(false);
        menu.findItem(R.id.ac_rel_ep).setVisible(false);
        menu.findItem(R.id.ac_reL_dl).setVisible(false);
        menu.findItem(R.id.ac_sk_p).setVisible(false);
        menu.findItem(R.id.ac_sk_c).setVisible(false);
        menu.findItem(R.id.ac_sk_m).setVisible(false);
        menu.findItem(R.id.ac_ex).setVisible(false);
        switch (CURRENT_FRAGMENT) {
            case FR_UpdateFrame:
                break;
            case FR_HFragment:
            case FR_HEFragment:
//            case Frag_SIFragment:
                menu.findItem(R.id.ac_rel).setVisible(true);
                break;
            case FR_DLFragment:
                menu.findItem(R.id.ac_ad).setVisible(true);
                menu.findItem(R.id.ac_reL_dl).setVisible(true);
                break;
            case FR_S2Fragment:
                menu.findItem(R.id.ac_rel_ed).setVisible(true);
                break;
            case FR_S3Fragment:
                menu.findItem(R.id.ac_rel_ep).setVisible(true);
                menu.findItem(R.id.ac_sk_p).setVisible(true);
                break;
            case FR_S4Fragment:
                menu.findItem(R.id.ac_sk_c).setVisible(true);
                break;
            case FR_S5Fragment:
                menu.findItem(R.id.ac_sk_m).setVisible(true);
                break;
            case FR_DFragment:
                break;
            case FR_IFragment:
                menu.findItem(R.id.ac_type_i).setVisible(true);
                break;
            case FR_TFragment:
                menu.findItem(R.id.ac_type_t).setVisible(true);
                break;
            case FR_PUFragment:
                menu.findItem(R.id.ac_att_pu).setVisible(true);
                break;
            case FR_ATHFragment:
                menu.findItem(R.id.ac_att_his).setVisible(false);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ac_type_i:
                IFragment.getInstance().showListMenu();
                return true;
            case R.id.ac_type_t:
                TFragment.getInstance().showListMenu();
                return true;
            case R.id.ac_rel:
                switch (CURRENT_FRAGMENT) {
                    case FR_HFragment:
                        HFragment.getInstance().CallD();
                        break;
                    case FR_HEFragment:
                        HEFragment.getInstance().CallED();
                        break;
                    case Frag_SIFragment:
                        SIFragment.getInstance().CallST();
                        break;
                }
                return true;
            case R.id.ac_reL_dl:
                DLFragment.getInstance().CallDL();
                return true;
            case R.id.ac_rel_ed:
                S2Fragment.getInstance().CallEDL();
                return true;
            case R.id.ac_ad:
                DLFragment.getInstance().OpADLFragment();
                return true;
            case R.id.ac_sk_p:
                S3Fragment.getInstance().skipP();
                return true;
            case R.id.ac_sk_c:
                S4Fragment.getInstance().skipC();
                return true;
            case R.id.ac_sk_m:
                S5Fragment.getInstance().skipM();
                return true;
            case R.id.ac_att_pu:
            case R.id.ac_att_his:
            case R.id.ac_ex:
                closeSession(getActivity());
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
