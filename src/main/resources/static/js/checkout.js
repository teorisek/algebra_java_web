document.addEventListener("DOMContentLoaded", function() {
    const paymentMethod = document.getElementById('paymentMethod');
    const paypalContainer = document.getElementById('paypal-button-container');
    const placeOrderBtn = document.getElementById('placeOrderBtn');
    const checkoutForm = document.getElementById('checkoutForm');

    function togglePayPal() {
        if (paymentMethod.value === 'PAYPAL') {
            paypalContainer.style.display = 'block';
            placeOrderBtn.style.display = 'none';
        } else {
            paypalContainer.style.display = 'none';
            placeOrderBtn.style.display = 'block';
        }
    }

    paymentMethod.addEventListener('change', togglePayPal);
    togglePayPal();

    if (window.paypal) {
        paypal.Buttons({
            createOrder: function(data, actions) {
                const amount = document.getElementById('amount').value;
                return actions.order.create({
                    purchase_units: [{
                        amount: { value: amount }
                    }]
                });
            },
            onApprove: function(data, actions) {
                return actions.order.capture().then(function(details) {
                    const formData = new FormData(checkoutForm);
                    formData.set('paymentMethod', 'PAYPAL');
                    formData.append('paypalOrderId', data.orderID);

                    fetch('/cart/checkout', {
                        method: 'POST',
                        body: formData,
                        headers: {
                            'X-Requested-With': 'XMLHttpRequest'
                        }
                    })
                        .then(response => {
                            if (response.redirected) {
                                window.location.href = response.url;
                            } else {
                                alert('Order placed successfully!');
                                window.location.href = '/cart/checkout-success';
                            }
                        })
                        .catch(error => {
                            alert('Error processing order: ' + error);
                        });
                });
            }
        }).render('#paypal-button-container');
    }
});
