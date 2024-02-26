package fithou.tuplv.quanghungglassesapi.utils;

public class Constants {
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_STAFF = "ROLE_STAFF";
    public static final String ROLE_MANAGER = "ROLE_MANAGER";
    public static final String ROLE_SALES = "ROLE_SALES";
    public static final String ROLE_WAREHOUSE = "ROLE_WAREHOUSE";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ADMIN_EMAIL = "adminquanghung@gmail.com";
    public static final String ADMIN_USERNAME = "adminquanghung";
    public static final String ADMIN_PASSWORD = "123";

    public static final String ERROR_USER_NOT_FOUND = "Người dùng không tồn tại";
    public static final String ERROR_ADDRESS_NOT_FOUND = "Địa chỉ không tồn tại";
    public static final String ERROR_PHONE_ALREADY_EXISTS = "Số điện thoại đã tồn tại";
    public static final String ERROR_EMAIL_ALREADY_EXISTS = "Email đã tồn tại";
    public static final String ERROR_EMAIL_NOT_FOUND = "Email không tồn tại";
    public static final String ERROR_VERIFICATION_CODE_INVALID = "Mã xác minh không hợp lệ";
    public static final String ERROR_VERIFICATION_CODE_EXPIRED = "Mã xác minh đã hết hạn";
    public static final String SUCCESS_REGISTER = "Đăng ký tài khoản thành công";
    public static final String SUCCESS_VERIFY_EMAIL = "Xác minh email thành công";
    public static final String SUCCESS_RESEND_VERIFICATION_CODE = "Đã gửi lại mã xác minh";
    public static final String ERROR_ACCOUNT_IS_LOCKED = "Tài khoản của bạn bị khóa";
    public static final String ERROR_PASSWORD_CONFIRM_MUST_MATCH_NEW = "Mật khẩu xác nhận phải trùng với mật khẩu mới";
    public static final String ERROR_PASSWORD_OLD_INVALID = "Mật khẩu cũ không đúng";
    public static final String ERROR_PASSWORD_NEW_MUST_DIFFERENT_OLD = "Mật khẩu mới phải khác mật khẩu cũ";
    public static final String ERROR_STAFF_HAS_RECEIPTS = "Nhân viên đang có hóa đơn";
    public static final String ERROR_STAFF_HAS_ORDERS = "Nhân viên đang có đơn hàng";
    public static final String ERROR_STAFF_HAS_WARRANTY = "Nhân viên đang có phiếu bảo hành";

    public static final String ERROR_BANNER_NOT_FOUND = "Banner không tồn tại";

    public static final String ERROR_CATEGORY_NOT_FOUND = "Danh mục không tồn tại";
    public static final String ERROR_CATEGORY_NAME_ALREADY_EXISTS = "Tên danh mục đã tồn tại";
    public static final String ERROR_CATEGORY_HAS_PRODUCTS = "Danh mục đang có sản phẩm";

    public static final String ERROR_SLUG_ALREADY_EXISTS = "Slug đã tồn tại";

    public static final String ERROR_MATERIAL_NOT_FOUND = "Chất liệu không tồn tại";
    public static final String ERROR_MATERIAL_ALREADY_EXISTS = "Chất liệu đã tồn tại";
    public static final String ERROR_MATERIAL_HAS_PRODUCTS = "Chất liệu đang có sản phẩm";

    public static final String ERROR_ORIGIN_NOT_FOUND = "Xuất xứ không tồn tại";
    public static final String ERROR_ORIGIN_ALREADY_EXISTS = "Xuất xứ đã tồn tại";
    public static final String ERROR_ORIGIN_HAS_PRODUCTS = "Xuất xứ đang có sản phẩm";

    public static final String ERROR_SHAPE_NOT_FOUND = "Hình dạng không tồn tại";
    public static final String ERROR_SHAPE_ALREADY_EXISTS = "Hình dạng đã tồn tại";
    public static final String ERROR_SHAPE_HAS_PRODUCTS = "Hình dạng đang có sản phẩm";

    public static final String ERROR_BRAND_NOT_FOUND = "Thương hiệu không tồn tại";
    public static final String ERROR_BRAND_ALREADY_EXISTS = "Thương hiệu đã tồn tại";
    public static final String ERROR_BRAND_HAS_PRODUCTS = "Thương hiệu đang có sản phẩm";

    public static final String ERROR_SUPPLIER_NOT_FOUND = "Nhà cung cấp không tồn tại";
    public static final String ERROR_SUPPLIER_NAME_ALREADY_EXISTS = "Tên nhà cung cấp đã tồn tại";
    public static final String ERROR_SUPPLIER_HAS_RECEIPT = "Nhà cung cấp đang có hóa đơn";

    public static final String ERROR_PRODUCT_NOT_FOUND = "Sản phẩm không tồn tại";
    public static final String ERROR_PRODUCT_DETAILS_NOT_FOUND = "Chi tiết sản phẩm không tồn tại";
    public static final String ERROR_PRODUCT_NAME_ALREADY_EXISTS = "Tên sản phẩm đã tồn tại";
    public static final String ERROR_PRODUCT_DETAILS_COLOR_ALREADY_EXISTS = "Màu sắc của sản phẩm đã tồn tại";
    public static final String ERROR_PRODUCT_THUMBNAIL_NOT_EMPTY = "Ảnh sản phẩm không được để trống";
    public static final String ERROR_PRODUCT_IMAGE_NOT_EMPTY = "Ảnh sản phẩm không được để trống";
    public static final String ERROR_PRODUCT_DETAILS_HAS_ORDER = "Chi tiết sản phẩm đang có đơn hàng";
    public static final String ERROR_PRODUCT_DETAILS_HAS_RECEIPT = "Chi tiết sản phẩm đang có hóa đơn";
    public static final String ERROR_PRODUCT_DETAILS_HAS_WARRANTY = "Chi tiết sản phẩm đang có phiếu bảo hành";

    public static final String DIR_FILE_BANNER = "banners";
    public static final String DIR_FILE_PRODUCT = "products";
    public static final String DIR_FILE_STAFF = "staffs";
    public static final String DIR_FILE_CUSTOMER = "customers";

    public static final String SORT_ASC = "asc";
    public static final String SORT_DESC = "desc";
    public static final String DEFAULT_PAGE_SIZE = "10"; // số lượng bản ghi mặc định trên 1 trang
    public static final String DEFAULT_PAGE_NUMBER = "0"; // số trang mặc định

    public static final String STORE_NAME = "Quang Hưng Glasses";
    public static final String FROM_EMAIL = "tupham1120@gmail.com";
}
