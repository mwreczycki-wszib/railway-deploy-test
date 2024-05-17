package com.example.payu;

import com.example.payu.payments.PayUService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/")
public class PayUController {

    private static final Logger logger = LoggerFactory.getLogger(PayUController.class);

    @Autowired
    private PayUService payUService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ApplicationConfig applicationConfig;


    @GetMapping
    public String home(Model model) {
        model.addAttribute("checkout", new Checkout());
        return "payu";
    }

    @PostMapping("/kup")
    public RedirectView kup(Checkout checkout) {
        return new RedirectView(payUService.postOrder(checkout));
    }

    @GetMapping("/thank-you")
    public String thankYou() {
        return "thank-you";
    }

    @PostMapping("/notify")
    public ResponseEntity nofity(@RequestParam("secret") String secret, @RequestBody PayUNotification notification) throws JsonProcessingException {
        if(!applicationConfig.getPayU().getNotificationSecret().equals(secret)) {
            return ResponseEntity.notFound().build();
        }
        logger.info("Payment notification recieved with status {}", notification.getOrder().getStatus());

        logger.info(objectMapper.writeValueAsString(notification));

        return ResponseEntity.ok().build();
    }

}
