package web.com.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart_24110366 implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final int MIN_QUANTITY = 1;
    public static final int MAX_QUANTITY = 10; // Giới hạn tối đa 10 sản phẩm / mặt hàng

    private Map<String, CartItem_24110366> items = new LinkedHashMap<>();

    public Cart_24110366() {
    }

    public Map<String, CartItem_24110366> getMap() {
        return items;
    }

    public Collection<CartItem_24110366> getItems() {
        return items.values();
    }

    /**
     * Thêm sản phẩm vào giỏ hàng với kiểm soát giới hạn
     * @return Thông báo trạng thái nếu có
     */
    public String add(Video_24110366 video, int qty) {
        if (video == null || video.getVideoId() == null) {
            return "Sản phẩm không hợp lệ!";
        }

        String id = video.getVideoId();
        CartItem_24110366 item = items.get(id);

        if (item == null) {
            int newQty = Math.max(MIN_QUANTITY, Math.min(qty, MAX_QUANTITY));
            items.put(id, new CartItem_24110366(video, newQty, video.getPrice()));
            return "Đã thêm sản phẩm vào giỏ hàng!";
        } else {
            int newQty = item.getQuantity() + qty;
            if (newQty > MAX_QUANTITY) {
                item.setQuantity(MAX_QUANTITY);
                return "Đã đạt số lượng tối đa cho phép (" + MAX_QUANTITY + " sản phẩm) đối với mặt hàng này!";
            } else if (newQty < MIN_QUANTITY) {
                item.setQuantity(MIN_QUANTITY);
                return "Số lượng tối thiểu là " + MIN_QUANTITY + "!";
            } else {
                item.setQuantity(newQty);
                return "Đã cập nhật số lượng trong giỏ hàng!";
            }
        }
    }

    /**
     * Cập nhật số lượng sản phẩm trực tiếp với giới hạn MIN/MAX
     */
    public String update(String videoId, int qty) {
        if (videoId == null || !items.containsKey(videoId)) {
            return "Không tìm thấy sản phẩm trong giỏ hàng!";
        }

        if (qty <= 0) {
            items.remove(videoId);
            return "Đã xóa sản phẩm khỏi giỏ hàng!";
        }

        if (qty > MAX_QUANTITY) {
            items.get(videoId).setQuantity(MAX_QUANTITY);
            return "Số lượng đã được giới hạn về mức tối đa cho phép (" + MAX_QUANTITY + ")!";
        }

        items.get(videoId).setQuantity(qty);
        return "Cập nhật số lượng thành công!";
    }

    /**
     * Xóa 1 sản phẩm khỏi giỏ
     */
    public void remove(String videoId) {
        if (videoId != null) {
            items.remove(videoId);
        }
    }

    /**
     * Làm rỗng toàn bộ giỏ hàng
     */
    public void clear() {
        items.clear();
    }

    /**
     * Tổng số lượng sản phẩm (phục vụ badge trên header)
     */
    public int getTotalQuantity() {
        int total = 0;
        for (CartItem_24110366 item : items.values()) {
            total += item.getQuantity();
        }
        return total;
    }

    /**
     * Tổng tiền của toàn bộ giỏ hàng
     */
    public double getTotalAmount() {
        double total = 0;
        for (CartItem_24110366 item : items.values()) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public String getFormattedTotalAmount() {
        DecimalFormat df = new DecimalFormat("###,### VNĐ");
        return df.format(getTotalAmount());
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
