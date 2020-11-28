package com.payment.pay.monetbil;

public class PojoMb {
 
  private String status,channel_name,paymentId,message,channel,channel_ussd,flow;

    public PojoMb() {
    }

    public PojoMb(String status, String channel_name, String paymentId, String message, String channel, String channel_ussd, String flow) {
        this.status = status;
        this.channel_name = channel_name;
        this.paymentId = paymentId;
        this.message = message;
        this.channel = channel;
        this.channel_ussd = channel_ussd;
        this.flow = flow;
    }

    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

   

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannel_ussd() {
        return channel_ussd;
    }

    public void setChannel_ussd(String channel_ussd) {
        this.channel_ussd = channel_ussd;
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

  
  
  
}
