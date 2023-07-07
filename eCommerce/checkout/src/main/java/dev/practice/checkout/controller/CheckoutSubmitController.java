package dev.practice.checkout.controller;

import dev.practice.checkout.service.CheckoutDto;
import dev.practice.checkout.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CheckoutSubmitController {

    private final CheckoutService checkoutService;

    @PostMapping("/checkout/submit")
    public String checkoutSubmit(CheckoutDto checkoutDto, Model model) {

        log.info("checkoutSubmit");
        log.info(checkoutDto.toString());

        Long checkoutId = checkoutService.saveCheckoutData(checkoutDto);

        model.addAttribute("checkoutId", checkoutId);

        return "submitComplete";
    }
}
