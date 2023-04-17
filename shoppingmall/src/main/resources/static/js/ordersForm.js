function requestPay() {
    IMP.init('imp89950763'); // 가맹점 식별코드
    IMP.request_pay({
        pg : 'html5_inicis',
        pay_method : 'card',
        merchant_uid: document.getElementById("num").value, // 상점에서 관리하는 주문 번호
        name : document.getElementById("productName").value,
        amount : document.getElementById("productPrice").value,
        buyer_name : document.getElementById("ordererName").value,
        buyer_email : document.getElementById("ordererEmailId").value + '@' + document.getElementById("ordererEmailAddress").value,
        buyer_tel : document.getElementById("ordererPhone1").value + '-' + document.getElementById("ordererPhone2").value + '-' + document.getElementById("ordererPhone3").value,
        buyer_postcode : '123-456',
        buyer_addr : document.getElementById("deliveryAddress").value + ' ' + document.getElementById("deliveryExtraAddress").value + ' ' + document.getElementById("deliveryDetailAddress").value
    }, function(rsp) {
        if ( rsp.success ) {
            //[1] 서버단에서 결제정보 조회를 위해 jQuery ajax로 imp_uid 전달하기
            $.ajax({
                url: "/orders/purchase", //cross-domain error가 발생하지 않도록 주의해주세요
                type: 'POST',
                dataType: 'json',
                data: {
                    imp_uid : rsp.imp_uid
                    //기타 필요한 데이터가 있으면 추가 전달
                }
            }).done(function(data) {
                //[2] 서버에서 REST API로 결제정보확인 및 서비스루틴이 정상적인 경우
                if ( everythings_fine ) {
                    var msg = '결제가 완료되었습니다.';
                    msg += '\n고유ID : ' + rsp.imp_uid;
                    msg += '\n상점 거래ID : ' + rsp.merchant_uid;
                    msg += '\n결제 금액 : ' + rsp.paid_amount;
                    msg += '카드 승인번호 : ' + rsp.apply_num;

                    alert(msg);
                } else {
                    //[3] 아직 제대로 결제가 되지 않았습니다.
                    //[4] 결제된 금액이 요청한 금액과 달라 결제를 자동취소처리하였습니다.
                }
            });
            location.href='list';
            document.getElementById("purchase").submit();
        } else {
            var msg = '결제에 실패하였습니다.';
            msg += '\n에러내용 : ' + rsp.error_msg;

            alert(msg);
        }
    });
}