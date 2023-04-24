package com.project.shoppingmall.product;

import com.project.shoppingmall.account.User;
import com.project.shoppingmall.account.UserRepository;
import com.project.shoppingmall.cart.CartRequestDto;
import com.project.shoppingmall.goods.Goods;
import com.project.shoppingmall.goods.GoodsRepository;
import com.project.shoppingmall.goods.GoodsRequestDto;
import com.project.shoppingmall.goods.GoodsService;
import com.project.shoppingmall.product_option.ProductColor;
import com.project.shoppingmall.product_option.ProductColorRepository;
import com.project.shoppingmall.product_option.ProductSize;
import com.project.shoppingmall.product_option.ProductSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductColorRepository productColorRepository;

    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping("/add_option_color")
    public String getProductAddOptionColor() {

        return "product/add_option_color";
    }

    @PostMapping("/add_option_color")
    public String postProductAddOptionColor(ProductColor productColor) {

        productService.productColorSave(productColor);

        return "product/add_option_color";
    }

    @GetMapping("/add_option_size")
    public String getProductAddOptionSize() {

        return "product/add_option_size";
    }

    @PostMapping("/add_option_size")
    public String postProductAddOptionSize(ProductSize productSize) {

        productService.productSizeSave(productSize);

        return "product/add_option_size";
    }

    @GetMapping("/option_register_list")
    public String getProductOptionRegisterList(Model model) {

        model.addAttribute("productResponseDtoList", productService.getProductList());

        return "product/option_register_list";
    }

    @GetMapping("/option_register")
    public String getProductOptionRegister(@RequestParam(required = false) Long id, Model model) {

        Product product = productRepository.findById(id).orElse(null);
        model.addAttribute("product", product);

        List<ProductColor> productColorList = productColorRepository.findAll();
        model.addAttribute("productColorList", productColorList);

        List<ProductSize> productSizeList = productSizeRepository.findAll();
        model.addAttribute("productSizeList", productSizeList);

        return "product/option_register";
    }

    @PostMapping("/option_register/{productId}")
    public String postProductOptionRegister(@PathVariable("productId") Long productId, @RequestParam(required = false) String color, @RequestParam(required = false) String size, @RequestParam(required = false) Integer quantity, GoodsRequestDto goodsRequestDto) {
        Product product = productRepository.findById(productId).orElse(null);
        goodsService.save(product, color, size, quantity, goodsRequestDto);
        return "redirect:/product/option_register?id={productId}";
    }

    @GetMapping("/register")
    public String getProductRegister() {

        return "product/register";
    }

    @PostMapping("/register")
    public String postProductRegister(ProductRequestDto productRequestDto, RedirectAttributes redirectAttributes) {

        productService.productRegister(productRequestDto);

        return "product/register";
    }

    @GetMapping("/list")
    public String getProductList(@RequestParam(required = false) String subCategoryId, Model model) {

        model.addAttribute("productResponseDtoList", productService.getProductList());

/*        List<ProductResponseDto> productResponseDto = productService.findBySubCategoryId(subCategoryId);
        model.addAttribute("product", productResponseDto);*/

        return "product/list";
    }

    @GetMapping("/detail")
    public String getProductDetail(Model model, Authentication authentication, @RequestParam(required = false) Long id, CartRequestDto cartRequestDto) {

        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);

        Product product = productRepository.findById(id).orElse(null);
        model.addAttribute("product", product);

        List<Goods> goodsList = goodsRepository.findAll();
        model.addAttribute("goodsList", goodsList);

        List<ReviewResponseDto> reviewResponseDtoList = reviewService.getReviewList(product);
        model.addAttribute("reviewResponseDto", reviewResponseDtoList);

        int reviewCount = reviewResponseDtoList.size();
        model.addAttribute("reviewCount", reviewCount);

        return "product/detail";
    }

    @PostMapping("/review")
    public String postProductReview(ReviewRequestDto reviewRequestDto) {

        reviewService.reviewRegister(reviewRequestDto);

        Long productId = reviewRequestDto.getProduct().getId();

        return "redirect:/product/detail?id="+productId;
    }

}