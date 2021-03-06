package me.panpf.sketch.sample.item

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.view.ViewGroup
import me.panpf.adapter.AssemblyItem
import me.panpf.adapter.AssemblyItemFactory
import me.panpf.adapter.ktx.bindView
import me.panpf.sketch.sample.ImageOptions
import me.panpf.sketch.sample.R
import me.panpf.sketch.sample.AppConfig
import me.panpf.sketch.sample.widget.SampleImageView
import me.panpf.sketch.util.SketchUtils

class MyPhotoItemFactory(private val onImageClickListener: OnImageClickListener?) : AssemblyItemFactory<String>() {
    private var itemSize: Int = 0

    override fun match(o: Any?): Boolean {
        return o is String
    }

    override fun createAssemblyItem(viewGroup: ViewGroup): PhotoAlbumItem {
        if (itemSize == 0) {
            itemSize = -1
            if (viewGroup is RecyclerView) {
                val spanCount = when (viewGroup.layoutManager) {
                    is GridLayoutManager -> (viewGroup.layoutManager as GridLayoutManager).spanCount
                    is StaggeredGridLayoutManager -> (viewGroup.layoutManager as StaggeredGridLayoutManager).spanCount
                    else -> 1
                }
                if (spanCount > 1) {
                    val screenWidth = viewGroup.getResources().displayMetrics.widthPixels
                    itemSize = (screenWidth - SketchUtils.dp2px(viewGroup.getContext(), 4) * (spanCount + 1)) / spanCount
                }
            }
        }

        return PhotoAlbumItem(R.layout.list_item_my_photo, viewGroup)
    }

    interface OnImageClickListener {
        fun onClickImage(position: Int, optionsKey: String)
    }

    inner class PhotoAlbumItem(itemLayoutId: Int, parent: ViewGroup) : AssemblyItem<String>(itemLayoutId, parent) {
        val imageView: SampleImageView by bindView(R.id.image_myPhotoItem)

        override fun onConfigViews(context: Context) {
            imageView.onClickListener = View.OnClickListener {
                onImageClickListener?.onClickImage(adapterPosition, imageView.optionsKey)
            }

            if (itemSize > 0) {
                imageView.layoutParams?.let {
                    it.width = itemSize
                    it.height = itemSize
                    imageView.layoutParams = it
                }
            }

            imageView.page = SampleImageView.Page.PHOTO_LIST
        }

        override fun onSetData(i: Int, imageUri: String?) {
            if (AppConfig.getBoolean(imageView.context, AppConfig.Key.SHOW_ROUND_RECT_IN_PHOTO_LIST)) {
                imageView.setOptions(ImageOptions.ROUND_RECT)
            } else {
                imageView.setOptions(ImageOptions.RECT)
            }

            imageView.displayImage(imageUri ?: "")
        }
    }
}
