package cn.flowerinsnow.greatscrollabletooltips.common.object;

import java.util.Objects;

/**
 * <p>代表一个滚动状态会话</p>
 * @param <I> ItemStack 类型
 */
public class ScrollSession<I> {
    /**
     * <p>当前垂直滚动值</p>
     */
    private int horizontal;
    /**
     * <p>当前水平滚动值</p>
     */
    private int vertical;

    /**
     * <p>最后一次渲染的物品</p>
     * <p>记录以方便判断是否要回正</p>
     */
    private I lastItemStackRendered;

    public int getHorizontal() {
        return this.horizontal;
    }

    public void setHorizontal(int horizontal) {
        this.horizontal = horizontal;
    }

    public void addHorizontal(int value) {
        this.horizontal += value;
    }

    public int getVertical() {
        return this.vertical;
    }

    public void setVertical(int vertical) {
        this.vertical = vertical;
    }

    public void addVertical(int value) {
        this.vertical += value;
    }

    public boolean isRendering() {
        return this.lastItemStackRendered != null;
    }

    public I getLastItemStackRendered() {
        return this.lastItemStackRendered;
    }

    public void setLastItemStackRendered(I lastItemStackRendered) {
        this.lastItemStackRendered = lastItemStackRendered;
    }

    /**
     * <p>滚动回正</p>
     */
    public void resetScroll() {
        this.horizontal = 0;
        this.vertical = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScrollSession<?> that = (ScrollSession<?>) o;
        return this.horizontal == that.horizontal && this.vertical == that.vertical && Objects.equals(this.lastItemStackRendered, that.lastItemStackRendered);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.horizontal;
        result = 31 * result + this.vertical;
        result = 31 * result + (this.lastItemStackRendered != null ? this.lastItemStackRendered.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ScrollSession{" +
                "horizontal=" + this.horizontal +
                ", vertical=" + this.vertical +
                ", lastItemStackRendered=" + this.lastItemStackRendered +
                '}';
    }
}
