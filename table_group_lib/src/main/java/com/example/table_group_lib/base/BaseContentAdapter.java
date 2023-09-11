package com.example.table_group_lib.base;

import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.table_group_lib.R;
import com.example.table_group_lib.TableScrollView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author：WangJianFeng
 * @date：2022/11/4
 * @description：
 */
public abstract class BaseContentAdapter<VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {
    protected int mLayoutResId;
    protected LayoutInflater mLayoutInflater;
    protected List<ITableContent> tableContentList = new ArrayList<>();

    public void refreshData(List<ITableContent> listData) {
        if (null != listData) {
            tableContentList.clear();
            tableContentList.addAll(listData);
        }
        notifyDataSetChanged();
    }

    public BaseContentAdapter(@LayoutRes int layoutResId) {
        if (layoutResId != 0) {
            mLayoutResId = layoutResId;
        }
    }

    protected View getItemView(@LayoutRes int layoutResId, ViewGroup parent) {
        return mLayoutInflater.inflate(layoutResId, parent, false);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mLayoutInflater = LayoutInflater.from(parent.getContext());
        return createBaseViewHolder(getItemView(mLayoutResId,parent));
    }


    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        convert(holder, getItem(position));
    }

    @Override
    public int getItemCount() {
        return tableContentList.size();
    }

    protected abstract void convert(@NonNull VH helper, ITableContent item);


    public ITableContent getItem(@IntRange(from = 0) int position) {
        if (position >= 0 && position < tableContentList.size())
            return tableContentList.get(position);
        else
            return null;
    }

    @SuppressWarnings("unchecked")
    protected VH createBaseViewHolder(View view) {
        Class temp = getClass();
        Class z = null;
        while (z == null && null != temp) {
            z = getInstancedGenericKClass(temp);
            temp = temp.getSuperclass();
        }
        VH k;
        // 泛型擦除会导致z为null
        if (z == null) {
            k = (VH) new BaseViewHolder(view);
        } else {
            k = createGenericKInstance(z, view);
        }
        return k != null ? k : (VH) new BaseViewHolder(view);
    }

    @SuppressWarnings("unchecked")
    private VH createGenericKInstance(Class z, View view) {
        try {
            Constructor constructor;
            // inner and unstatic class
            if (z.isMemberClass() && !Modifier.isStatic(z.getModifiers())) {
                constructor = z.getDeclaredConstructor(getClass(), View.class);
                constructor.setAccessible(true);
                return (VH) constructor.newInstance(this, view);
            } else {
                constructor = z.getDeclaredConstructor(View.class);
                constructor.setAccessible(true);
                return (VH) constructor.newInstance(view);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Class getInstancedGenericKClass(Class z) {
        Type type = z.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            for (Type temp : types) {
                if (temp instanceof Class) {
                    Class tempClass = (Class) temp;
                    if (BaseViewHolder.class.isAssignableFrom(tempClass)) {
                        return tempClass;
                    }
                } else if (temp instanceof ParameterizedType) {
                    Type rawType = ((ParameterizedType) temp).getRawType();
                    if (rawType instanceof Class && BaseViewHolder.class.isAssignableFrom((Class<?>) rawType)) {
                        return (Class<?>) rawType;
                    }
                }
            }
        }
        return null;
    }
}
