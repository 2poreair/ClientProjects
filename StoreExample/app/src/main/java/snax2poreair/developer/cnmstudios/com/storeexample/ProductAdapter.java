package snax2poreair.developer.cnmstudios.com.storeexample;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by 2poreSnaxA on 1/20/2018.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private final String TAG = "PRODUCTADAPTER";

    private Context context;
    private List<Product> mList;
    MySingleton singleton;
    private ImageLoader mImageLoader;

    public ProductAdapter(Context ctx, List<Product> plist){
        context = ctx;
        mList = plist;

        mImageLoader = new ImageLoader(singleton.getInstance(context).getRequestQueue(), new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "+++ PRODUCT VIEW HOLDER +++");

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cardview, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        Log.d(TAG, "+++ ON BIND VIEW HOLDER +++");
        final String label;

        Product prod = mList.get(position);
        holder.title.setText(prod.getTitle());
        holder.cost.setText(prod.getCost());
        label = prod.getTitle();
        holder.prodimg.setImageUrl(prod.getUrl(), mImageLoader);
        holder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context.getApplicationContext(), "Clicked " + label, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView title, cost;
        NetworkImageView prodimg;
        ImageButton buy;

        public ProductViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tvpname);
            prodimg = itemView.findViewById(R.id.niview);
            buy = itemView.findViewById(R.id.btnbuy);
            cost = itemView.findViewById(R.id.tvcost);
        }
    }
}
