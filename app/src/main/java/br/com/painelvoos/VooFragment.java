/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.painelvoos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VooFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private int position;
    private VooAdapter<Voo> voosAdapter;
    private ListView voosLV;

    private List<Voo> voos;
    private boolean loadingMore;
    private int pagePartidas;
    private int pageChegadas;
    private String urlList;


    public static VooFragment newInstance(int position) {
        VooFragment f = new VooFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = null;

        if (position == 0) {
            view = inflater.inflate(R.layout.partidas, container, false);
            voosLV = (ListView) view.findViewById(R.id.partidas);
            urlList = "http://hawk393.startdedicated.com/flight/partidas?page=" + pagePartidas;
        } else {
            view = inflater.inflate(R.layout.chegadas, container, false);
            voosLV = (ListView) view.findViewById(R.id.voos);
            urlList = "http://hawk393.startdedicated.com/flight/chegadas?page=" + pageChegadas;
        }

        Thread thread = new Thread(null, flightRunnable);
        thread.start();

        return view;
    }

    private Runnable flightRunnable = new Runnable() {
        @Override
        public void run() {
            carregar();
        }
    };

    private void carregar() {

        try {
            String retorno = new RequestVooTask().execute(urlList).get();
            voos = parseJson(retorno);
        } catch (Exception e) {
            e.printStackTrace();
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                voosAdapter = new VooAdapter<Voo>(getActivity(), voos);
                voosLV.setAdapter(voosAdapter);
                voosLV.setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem,
                                         int visibleItemCount, int totalItemCount) {

                        //what is the bottom iten that is visible
                        int lastInScreen = firstVisibleItem + visibleItemCount;
                        //is the bottom item visible & not loading more already ? Load more !
                        if (lastInScreen != 0 && totalItemCount != 0 && (lastInScreen == totalItemCount) && !(loadingMore)) {
                            Thread thread = new Thread(null, loadMoreFlights);
                            thread.start();
                        }

                    }
                });
            }
        });

    }

    private Runnable loadMoreFlights = new Runnable() {
        @Override
        public void run() {

            if (position == 0) {
                pagePartidas++;
            } else {
                pageChegadas++;
            }

            loadingMore = true;
            voos = new ArrayList<Voo>();

            String retorno = null;
            try {
                retorno = new RequestVooTask().execute(urlList).get();
                voos = parseJson(retorno);
            } catch (Exception e) {
                e.printStackTrace();
            }
            getActivity().runOnUiThread(updateUI);
        }
    };

    private Runnable updateUI = new Runnable() {
        @Override
        public void run() {
            if (voos != null && !voos.isEmpty()) {
                for (Voo chegada : voos) {
                    voosAdapter.add(chegada);
                }
            }
            voosAdapter.notifyDataSetChanged();

            loadingMore = false;
        }
    };

    private List<Voo> parseJson(String json) {

        List<Voo> voos = new ArrayList<Voo>();

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < (jsonArray.length()); i++) {
            JSONObject obj = null;
            try {
                obj = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (obj != null) {
                try {
                    String id = obj.getString("Id");
                    String horarioEmbarque = obj.getString("FlightTime");
                    String destino = obj.getString("Destination");
                    String numeroVoo = obj.getString("FlightNumber");
                    String portao = obj.getString("Gate");
                    String status = obj.getString("Status");

                    Voo voo = new Voo(horarioEmbarque, destino, "", numeroVoo, "", portao, "", status);
                    voos.add(voo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return voos;
    }

}
