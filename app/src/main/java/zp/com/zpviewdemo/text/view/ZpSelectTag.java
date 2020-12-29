package zp.com.zpviewdemo.text.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import zp.com.zpviewdemo.R;

/**
 * Select Tag控件
 * 功能：
 * 1. 支持单选、多选
 * 2. 支持设置列数
 * 3. 支持设置行间距、列间距
 * 4. 支持设置item宽高及字体大小
 * 5. 支持屏幕自适应宽度模式，此模式间距固定，宽定根据列数自适应
 */
public class ZpSelectTag extends RecyclerView {

    /**
     * 默认列间距，20dp
     */
    private static final int DEFAULT_HORIZONTAL_SPACING = 20;
    /**
     * 默认行间距，11dp
     */
    private static final int DEFAULT_VERTICAL_SPACING = 11;
    /**
     * 默认列数，3
     */
    private static final int DEFAULT_COLUMNS = 3;
    /**
     * 默认item宽度，64dp
     */
    private static final int DEFAULT_ITEM_WIDTH = 64;
    /**
     * 默认item高度，20dp
     */
    private static final int DEFAULT_ITEM_HEIGHT = 20;
    /**
     * 多选上限未设置
     */
    private static final int MAX_SELECT_NUM_UNSET = -1;

    private HftSelectTagListener mListener;
    private GridLayoutManager mLayoutManager;
    private SelectTagAdapter mAdapter;

    private boolean isMultiSelect = false, isResponsive = false;
    private int mItemWidth, mItemHeight, mItemTextSize, mHorizontalSpacing, mVerticalSpacing, mMaxSelectNum;

    public ZpSelectTag(Context context) {
        super(context);
        init(context, null, 0);
    }

    public ZpSelectTag(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public ZpSelectTag(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SelectTag);
        // 原生属性
        mItemWidth = a.getDimensionPixelSize(R.styleable.SelectTag_itemWidth,
                TypedValue.complexToDimensionPixelSize(DEFAULT_ITEM_WIDTH, getResources().getDisplayMetrics()));
        mItemHeight = a.getDimensionPixelSize(R.styleable.SelectTag_itemHeight,
                TypedValue.complexToDimensionPixelSize(DEFAULT_ITEM_HEIGHT, getResources().getDisplayMetrics()));
        mItemTextSize = a.getDimensionPixelSize(R.styleable.SelectTag_itemTextSize, 0);
        isResponsive = a.getBoolean(R.styleable.SelectTag_responsive, isResponsive);
        isMultiSelect = a.getBoolean(R.styleable.SelectTag_multiSelect, isMultiSelect);
        mHorizontalSpacing = a.getDimensionPixelSize(R.styleable.SelectTag_horizontalSpacing,
                TypedValue.complexToDimensionPixelSize(DEFAULT_HORIZONTAL_SPACING, getResources().getDisplayMetrics()));
        mVerticalSpacing = a.getDimensionPixelSize(R.styleable.SelectTag_verticalSpacing,
                TypedValue.complexToDimensionPixelSize(DEFAULT_VERTICAL_SPACING, getResources().getDisplayMetrics()));
        int columns = a.getInt(R.styleable.SelectTag_columns, DEFAULT_COLUMNS);
        mMaxSelectNum = a.getInt(R.styleable.SelectTag_maxSelectNum, MAX_SELECT_NUM_UNSET);

        mLayoutManager = new GridLayoutManager(context, columns);
        this.setLayoutManager(mLayoutManager);

        SpacesItemDecoration itemDecoration = new SpacesItemDecoration(mHorizontalSpacing, mVerticalSpacing);
        this.addItemDecoration(itemDecoration);

        mAdapter = new SelectTagAdapter(context);
        this.setAdapter(mAdapter);
        a.recycle();
    }

    /**
     * 设置多选上限
     *
     * @param max
     */
    public void setMaxSelectNum(int max) {
        if (isMultiSelect) {
            this.mMaxSelectNum = max;
        }
    }

    /**
     * 设置是否多选，默认false
     *
     * @param isMultiSelect
     */
    public void setMultiSelect(boolean isMultiSelect) {
        this.isMultiSelect = isMultiSelect;
    }

    /**
     * 设置是否自适应item宽度
     *
     * @param isResponsive
     */
    public void setResponsive(boolean isResponsive) {
        this.isResponsive = isResponsive;
    }

    /**
     * 设置选中
     *
     * @param position 位置
     */
    public void addSelected(int position) {
        mAdapter.addSelect(position);
    }

    /**
     * 设置不选中
     *
     * @param position 位置
     */
    public void removeSelect(int position) {
        mAdapter.removeSelect(position);
    }

    /**
     * 设置不选中
     *
     * @param value 值
     */
    public void removeSelectObject(Integer value) {
        mAdapter.removeSelect(value);
    }

    /**
     * 清空所有选中项
     */
    public void clear() {
        mAdapter.clear();
    }

    /**
     * 设置数据
     *
     * @param origins 显示的item数据，显示内容为toString()内容，传入实体需实现toString()方法。
     */
    public void setData(Object[] origins) {
        if (origins == null || origins.length == 0) {
            return;
        }
        mAdapter.setData(Arrays.asList(origins));
    }

    public void setData(List origins) {
        if (origins == null || origins.size() == 0) {
            return;
        }
        mAdapter.setData(origins);
    }

    /**
     * 选择内容改变监听器
     *
     * @param listener
     */
    public void setSelectChangedListener(HftSelectTagListener listener) {
        this.mListener = listener;
    }

    /**
     * 获取选中项
     *
     * @return
     */
    public List<Integer> getSelected() {
        return mAdapter.selectPositions;
    }

    /**
     * Select Tag Listener
     */
    public interface HftSelectTagListener {

        /**
         * select change
         *
         * @param positions
         */
        void onSelectChanged(List<Integer> positions);
    }

    /**
     * 行间距、列间距实现
     */
    class SpacesItemDecoration extends ItemDecoration {

        private int horizontalSpacing;
        private int verticalSpacing;

        public SpacesItemDecoration(int horizontalSpacing, int verticalSpacing) {
            this.horizontalSpacing = horizontalSpacing;
            this.verticalSpacing = verticalSpacing;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {

            final int position = parent.getChildLayoutPosition(view);
            int span = mLayoutManager.getSpanCount();
            int totalCount = mLayoutManager.getItemCount();
            int surplusCount = totalCount % mLayoutManager.getSpanCount();
            // 是否为最后一排
            boolean isLastRow = surplusCount == 0 ?
                    position > totalCount - mLayoutManager.getSpanCount() - 1 :
                    position > totalCount - surplusCount - 1;
            // 是否为最后一列
            boolean isLastColumn = (position + 1) % span == 0;
            // 是否为第一列
            boolean isFirstColumn = position % span == 0;

            int bottom = 0;
            int right = 0;

            // 如果是自适应
            if (isResponsive) {
                setPadding(horizontalSpacing, 30, 0, 30);
            }

            // 非最后一列，设置right
            if (isResponsive || !isLastColumn) {
                right = horizontalSpacing;
            }

            // 不是最后一行，设置bottom
            if (totalCount > span && !isLastRow) {
                bottom = verticalSpacing;
            }
            outRect.set(0, 0, right, bottom);
        }
    }

    class SelectTagAdapter extends Adapter<SelectTagViewHolder> {

        private Context context;
        private List<Object> datas = new ArrayList<>();
        private List<Integer> selectPositions = new ArrayList<>();

        public SelectTagAdapter(Context context) {
            this.context = context;
        }

        public void setData(List<Object> origins) {
            if (!isMultiSelect) {
                mMaxSelectNum = 1;
            } else if (mMaxSelectNum == MAX_SELECT_NUM_UNSET) {
                mMaxSelectNum = origins.size();
            }

            this.datas.clear();
            this.datas.addAll(origins);
            this.notifyDataSetChanged();
        }

        public void addSelect(int position) {
            selectPositions.add(position);
            this.notifyDataSetChanged();
        }

        public void removeSelect(int position) {
            selectPositions.remove(position);
            this.notifyDataSetChanged();
        }

        public void removeSelect(Integer position) {
            selectPositions.remove(position);
            this.notifyDataSetChanged();
        }

        public void clear() {
            selectPositions.clear();
            this.notifyDataSetChanged();
        }

        @Override
        public SelectTagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView item = (TextView) LayoutInflater.from(context).inflate(R.layout.item_select_tag_layout, parent, false);
            ViewGroup.LayoutParams lp = item.getLayoutParams();
            if (isResponsive) {
                lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            } else {
                lp.width = mItemWidth;
            }
            lp.height = mItemHeight;
            item.setLayoutParams(lp);

            if (mItemTextSize > 0) {
                item.setTextSize(TypedValue.COMPLEX_UNIT_PX, mItemTextSize);
            }
            return new SelectTagViewHolder(item);
        }

        @Override
        public void onBindViewHolder(final SelectTagViewHolder holder, int position) {
            Object data = datas.get(position);
            if (data != null) {
                holder.tvTag.setText(data.toString());
            }

            holder.tvTag.setSelected(selectPositions.contains(position));
            holder.tvTag.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (selectPositions.contains(holder.getAdapterPosition())) {
                        selectPositions.remove(Integer.valueOf(holder.getAdapterPosition()));
                    } else {
                        if (!isMultiSelect && selectPositions.size() == 1) {
                            selectPositions.clear();
                        }

                        if (selectPositions.size() < mMaxSelectNum) {
                            selectPositions.add(holder.getAdapterPosition());
                        }
                    }
                    notifyDataSetChanged();
                    if (mListener != null) {
                        mListener.onSelectChanged(selectPositions);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return datas == null ? 0 : datas.size();
        }
    }

    class SelectTagViewHolder extends ViewHolder {

        private TextView tvTag;

        public SelectTagViewHolder(View itemView) {
            super(itemView);
            tvTag = (TextView) itemView.findViewById(R.id.tag_tv);
        }
    }
}

