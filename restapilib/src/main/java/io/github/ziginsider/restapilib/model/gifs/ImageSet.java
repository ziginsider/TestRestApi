package io.github.ziginsider.restapilib.model.gifs;

import com.google.gson.annotations.SerializedName;

class ImageSet {

    @SerializedName("fixed_height")
    private Image fixed_height;

    @SerializedName("fixed_height_still")
    private Image fixed_height_still;

    @SerializedName("fixed_height_downsampled")
    private Image fixed_height_downsampled;

    @SerializedName("fixed_width")
    private Image fixed_width;

    @SerializedName("fixed_width_still")
    private Image fixed_width_still;

    @SerializedName("fixed_width_downsampled")
    private Image fixed_width_downsampled;

    @SerializedName("fixed_height_small")
    private Image fixed_height_small;

    @SerializedName("fixed_height_small_sill")
    private Image fixed_height_small_sill;

    @SerializedName("fixed_width_small")
    private Image fixed_width_small;

    @SerializedName("fixed_width_small_still")
    private Image fixed_width_small_still;

    @SerializedName("downsized")
    private Image downsized;

    @SerializedName("downsized_still")
    private Image downsized_still;

    @SerializedName("downsized_large")
    private Image downsized_large;

    @SerializedName("original")
    private Image original;

    @SerializedName("original_still")
    private Image original_still;

    public Image getFixed_height() {
        return fixed_height;
    }

    public void setFixed_height(Image fixed_height) {
        this.fixed_height = fixed_height;
    }

    public Image getFixed_height_still() {
        return fixed_height_still;
    }

    public void setFixed_height_still(Image fixed_height_still) {
        this.fixed_height_still = fixed_height_still;
    }

    public Image getFixed_height_downsampled() {
        return fixed_height_downsampled;
    }

    public void setFixed_height_downsampled(Image fixed_height_downsampled) {
        this.fixed_height_downsampled = fixed_height_downsampled;
    }

    public Image getFixed_width() {
        return fixed_width;
    }

    public void setFixed_width(Image fixed_width) {
        this.fixed_width = fixed_width;
    }

    public Image getFixed_width_still() {
        return fixed_width_still;
    }

    public void setFixed_width_still(Image fixed_width_still) {
        this.fixed_width_still = fixed_width_still;
    }

    public Image getFixed_width_downsampled() {
        return fixed_width_downsampled;
    }

    public void setFixed_width_downsampled(Image fixed_width_downsampled) {
        this.fixed_width_downsampled = fixed_width_downsampled;
    }

    public Image getFixed_height_small() {
        return fixed_height_small;
    }

    public void setFixed_height_small(Image fixed_height_small) {
        this.fixed_height_small = fixed_height_small;
    }

    public Image getFixed_height_small_sill() {
        return fixed_height_small_sill;
    }

    public void setFixed_height_small_sill(Image fixed_height_small_sill) {
        this.fixed_height_small_sill = fixed_height_small_sill;
    }

    public Image getFixed_width_small() {
        return fixed_width_small;
    }

    public void setFixed_width_small(Image fixed_width_small) {
        this.fixed_width_small = fixed_width_small;
    }

    public Image getFixed_width_small_still() {
        return fixed_width_small_still;
    }

    public void setFixed_width_small_still(Image fixed_width_small_still) {
        this.fixed_width_small_still = fixed_width_small_still;
    }

    public Image getDownsized() {
        return downsized;
    }

    public void setDownsized(Image downsized) {
        this.downsized = downsized;
    }

    public Image getDownsized_still() {
        return downsized_still;
    }

    public void setDownsized_still(Image downsized_still) {
        this.downsized_still = downsized_still;
    }

    public Image getDownsized_large() {
        return downsized_large;
    }

    public void setDownsized_large(Image downsized_large) {
        this.downsized_large = downsized_large;
    }

    public Image getOriginal() {
        return original;
    }

    public void setOriginal(Image original) {
        this.original = original;
    }

    public Image getOriginal_still() {
        return original_still;
    }

    public void setOriginal_still(Image original_still) {
        this.original_still = original_still;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
