/**
 * ExtensionMapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.9  Built on : Nov 16, 2018 (12:06:07 GMT)
 */
package com.payment.pay.gtplimited;

/**
 * ExtensionMapper class
 */
@SuppressWarnings({"unchecked", "unused"})

public class ExtensionMapper {

    public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
            java.lang.String typeName,
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "FundTransferCardToCardRequestMobile".equals(typeName)) {

            return com.payment.pay.gtplimited.FundTransferCardToCardRequestMobile.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "PingRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.PingRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "BaseResponseHeader".equals(typeName)) {

            return com.payment.pay.gtplimited.BaseResponseHeader.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "CustomerSearchResponse".equals(typeName)) {

            return com.payment.pay.gtplimited.CustomerSearchResponse.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "GetCardDataRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.GetCardDataRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "ActivateUpgradeRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.ActivateUpgradeRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "PingResponse".equals(typeName)) {

            return com.payment.pay.gtplimited.PingResponse.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "BaseResponseBody".equals(typeName)) {

            return com.payment.pay.gtplimited.BaseResponseBody.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "PINCheckResponse".equals(typeName)) {

            return com.payment.pay.gtplimited.PINCheckResponse.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "ShipmoneyEmployeesDataRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.ShipmoneyEmployeesDataRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "TransactionActivityRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.TransactionActivityRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "FundTransferPanReverseResponse".equals(typeName)) {

            return com.payment.pay.gtplimited.FundTransferPanReverseResponse.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "ArrayOfTransactionActivityList".equals(typeName)) {

            return com.payment.pay.gtplimited.ArrayOfTransactionActivityList.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "GetPANRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.GetPANRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "ValidateRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.ValidateRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "TransactionActivityList".equals(typeName)) {

            return com.payment.pay.gtplimited.TransactionActivityList.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "VerifyCardRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.VerifyCardRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "FundTransferReverseByEmployeeIDRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.FundTransferReverseByEmployeeIDRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "FundTransferCardToCardRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.FundTransferCardToCardRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "TransactionActivityResponse".equals(typeName)) {

            return com.payment.pay.gtplimited.TransactionActivityResponse.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "CustomerUpgradeRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.CustomerUpgradeRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "FundTransferPanReverseRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.FundTransferPanReverseRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "SendSensitiveDataResponse".equals(typeName)) {

            return com.payment.pay.gtplimited.SendSensitiveDataResponse.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "ShipmoneyCustomerDataRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.ShipmoneyCustomerDataRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "ShipmoneyCustomerDataResponse".equals(typeName)) {

            return com.payment.pay.gtplimited.ShipmoneyCustomerDataResponse.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "DeactivateCardRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.DeactivateCardRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "TransactionStatusResponse".equals(typeName)) {

            return com.payment.pay.gtplimited.TransactionStatusResponse.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "ShipmoneyCustomerPartnerRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.ShipmoneyCustomerPartnerRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "ShipmoneyRegistrationResponse".equals(typeName)) {

            return com.payment.pay.gtplimited.ShipmoneyRegistrationResponse.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "DeactivateCardResponse".equals(typeName)) {

            return com.payment.pay.gtplimited.DeactivateCardResponse.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "BankInfoResponse".equals(typeName)) {

            return com.payment.pay.gtplimited.BankInfoResponse.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "ShipmoneyEmployeesData".equals(typeName)) {

            return com.payment.pay.gtplimited.ShipmoneyEmployeesData.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "ReactivateCardResponse".equals(typeName)) {

            return com.payment.pay.gtplimited.ReactivateCardResponse.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "ValidateLast4Request".equals(typeName)) {

            return com.payment.pay.gtplimited.ValidateLast4Request.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "FundTransferRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.FundTransferRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "VirtualRegistrationRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.VirtualRegistrationRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "TransactionDetailRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.TransactionDetailRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "GetCardDataResponse".equals(typeName)) {

            return com.payment.pay.gtplimited.GetCardDataResponse.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "ShipmoneyRegistrationRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.ShipmoneyRegistrationRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "CustomerInfo".equals(typeName)) {

            return com.payment.pay.gtplimited.CustomerInfo.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "StatusRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.StatusRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "GetCardInfoRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.GetCardInfoRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "BalanceRequestMobile".equals(typeName)) {

            return com.payment.pay.gtplimited.BalanceRequestMobile.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "ShipmoneyEmployeesDataResponse".equals(typeName)) {

            return com.payment.pay.gtplimited.ShipmoneyEmployeesDataResponse.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "ShipmoneyCustomerPartnerResponse".equals(typeName)) {

            return com.payment.pay.gtplimited.ShipmoneyCustomerPartnerResponse.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "ValidateResponse".equals(typeName)) {

            return com.payment.pay.gtplimited.ValidateResponse.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "VerifyCardResponse".equals(typeName)) {

            return com.payment.pay.gtplimited.VerifyCardResponse.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "TransactionDetailResponse".equals(typeName)) {

            return com.payment.pay.gtplimited.TransactionDetailResponse.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "PINCheckRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.PINCheckRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "InstantRegistrationRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.InstantRegistrationRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "FundTransferReverseResponse".equals(typeName)) {

            return com.payment.pay.gtplimited.FundTransferReverseResponse.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "CustomerUpdateResponse".equals(typeName)) {

            return com.payment.pay.gtplimited.CustomerUpdateResponse.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "CustomerSearchRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.CustomerSearchRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "RegistrationRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.RegistrationRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "SendSensitiveDataRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.SendSensitiveDataRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "FundTransferPanRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.FundTransferPanRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "BaseRequestHeader".equals(typeName)) {

            return com.payment.pay.gtplimited.BaseRequestHeader.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "ArrayOfCustomerInfo".equals(typeName)) {

            return com.payment.pay.gtplimited.ArrayOfCustomerInfo.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "FundTransferResponse".equals(typeName)) {

            return com.payment.pay.gtplimited.FundTransferResponse.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "VerifyTransferRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.VerifyTransferRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "CustomerUpdateRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.CustomerUpdateRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "FundTransferReverseByEmployeeIDResponse".equals(typeName)) {

            return com.payment.pay.gtplimited.FundTransferReverseByEmployeeIDResponse.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "FundTransferByEmployeeIDRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.FundTransferByEmployeeIDRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "GetPANResponse".equals(typeName)) {

            return com.payment.pay.gtplimited.GetPANResponse.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "BaseRequestBody".equals(typeName)) {

            return com.payment.pay.gtplimited.BaseRequestBody.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "CustomerIDRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.CustomerIDRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "ArrayOfShipmoneyEmployeesData".equals(typeName)) {

            return com.payment.pay.gtplimited.ArrayOfShipmoneyEmployeesData.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "TransactionStatusRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.TransactionStatusRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "UpgradeCardRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.UpgradeCardRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "CustomerDataRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.CustomerDataRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "BankInfoRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.BankInfoRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "ReactivateCardRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.ReactivateCardRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "UpgradeCardResponse".equals(typeName)) {

            return com.payment.pay.gtplimited.UpgradeCardResponse.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "RegistrationResponse".equals(typeName)) {

            return com.payment.pay.gtplimited.RegistrationResponse.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "BalanceRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.BalanceRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "FundTransferPanResponse".equals(typeName)) {

            return com.payment.pay.gtplimited.FundTransferPanResponse.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "StatusResponse".equals(typeName)) {

            return com.payment.pay.gtplimited.StatusResponse.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "FundTransferReverseRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.FundTransferReverseRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "ErrorResponse".equals(typeName)) {

            return com.payment.pay.gtplimited.ErrorResponse.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "GetCardInfoResponse".equals(typeName)) {

            return com.payment.pay.gtplimited.GetCardInfoResponse.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "BalanceResponse".equals(typeName)) {

            return com.payment.pay.gtplimited.BalanceResponse.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "GetCardImageRequest".equals(typeName)) {

            return com.payment.pay.gtplimited.GetCardImageRequest.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "CustomerDataResponse".equals(typeName)) {

            return com.payment.pay.gtplimited.CustomerDataResponse.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "GetCardImageResponse".equals(typeName)) {

            return com.payment.pay.gtplimited.GetCardImageResponse.Factory.parse(reader);

        }

        if ("http://gtplimited.com/".equals(namespaceURI)
                && "VerifyTransferResponse".equals(typeName)) {

            return com.payment.pay.gtplimited.VerifyTransferResponse.Factory.parse(reader);

        }

        throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
    }

}
