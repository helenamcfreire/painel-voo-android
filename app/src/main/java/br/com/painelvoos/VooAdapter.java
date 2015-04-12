package br.com.painelvoos;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.painelvoos.lazylist.ImageLoader;

/**
 * Created by helenamcfreire on 08/04/15.
 */
public class VooAdapter<P> extends ArrayAdapter<Voo> {

    private final LayoutInflater inflater;
    private List<Voo> voos;
    public ImageLoader imageLoader;
    private Typeface mainFont;
    private Context context;

    public VooAdapter(Context context, List<Voo> voos) {
        super(context, R.layout.row, voos);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.voos = new ArrayList<Voo>();
        this.voos.addAll(voos);
       // this.imageLoader = new ImageLoader(context);
        this.mainFont = Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Light.ttf");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Voo voo = getItem(position);

        convertView = inflater.inflate(R.layout.row, parent, false);

        //ImageView foto = (ImageView) convertView.findViewById(R.id.list_image);
        //imageLoader.DisplayImage(voo.getUrlLogoCiaAerea(), foto, R.drawable.ic_launcher_gplus);
        List<Drawable> fotos = new ArrayList<Drawable>();

        fotos.add(context.getResources().getDrawable(R.drawable.gol));
        fotos.add(context.getResources().getDrawable(R.drawable.azul));
        fotos.add(context.getResources().getDrawable(R.drawable.american_airlines));
        fotos.add(context.getResources().getDrawable(R.drawable.tam));

        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(fotos.size());
        Drawable foto = fotos.get(index);
        ImageView fotoIV = (ImageView) convertView.findViewById(R.id.logoCiaAerea);
        fotoIV.setImageDrawable(foto);

        TextView destinoTV = (TextView) convertView.findViewById(R.id.destino);
        destinoTV.setText(voo.getDestino());
        destinoTV.setTypeface(mainFont);

        TextView portaoTV = (TextView) convertView.findViewById(R.id.portao);
        portaoTV.setTypeface(mainFont);
        if (voo.getPortao().isEmpty()) {
            portaoTV.setText(" Portão -");
        } else {
            portaoTV.setText(" Portão " + voo.getPortao());
        }




        TextView horarioEmbarqueTV = (TextView) convertView.findViewById(R.id.horarioEmbarque);
        horarioEmbarqueTV.setText(voo.getHorario());
        horarioEmbarqueTV.setTypeface(mainFont);

        TextView statusTV = (TextView) convertView.findViewById(R.id.status);
        statusTV.setText(voo.getStatus());
        statusTV.setTypeface(mainFont);

        TextView numeroVooTV = (TextView) convertView.findViewById(R.id.numeroVoo);
        numeroVooTV.setText(voo.getNumeroVoo());
        numeroVooTV.setTypeface(mainFont);

        return convertView;
    }

}