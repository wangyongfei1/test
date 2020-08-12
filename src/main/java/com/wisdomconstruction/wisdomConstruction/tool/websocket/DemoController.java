package com.wisdomconstruction.wisdomConstruction.tool.websocket;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author wangyongfei
 * @date 2020/6/28 19:58
 */
@RestController
@RequestMapping("/pushData")
public class DemoController {

    @GetMapping("/push/{projectNo}")
    public ResponseEntity<String> pushToWeb(@RequestParam String message, @PathVariable String projectNo) throws IOException {
        WebSocketServer.sendInfo(message,projectNo);
        return ResponseEntity.ok("发送成功");
    }
}