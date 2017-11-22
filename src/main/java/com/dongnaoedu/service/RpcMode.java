package com.dongnaoedu.service;

import com.dongnaoedu.rpc.DepotService;
import com.dongnaoedu.rpc.RpcProxy;
import com.dongnaoedu.vo.GoodTransferVo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;

/**
 * @Description
 * @Author shichaogeng
 * @Create 2017-11-21 16:37
 */
@Service
@Qualifier("rpc")
public class RpcMode implements IProDepot {

    private static final String IP = "127.0.0.1";
    private static final int PORT = 10002;

    public void processDepot(String goodsId, int amount) {

        DepotService service = RpcProxy.getRmoteProxyObj(DepotService.class,
                new InetSocketAddress(IP,PORT));
        GoodTransferVo goodTransferVo = new GoodTransferVo();
        goodTransferVo.setGoodsId(goodsId);
        goodTransferVo.setChangeAmount(amount);
        goodTransferVo.setInOrOut(false);
        service.changeDepot(goodTransferVo);
    }
}
