<template>
    <div>

        <paypal></paypal>



    </div>
</template>

<script>

    import paypal from '~/components/Paypal'
    export default {
        name: "recharge-my-account",
        components : {
            paypal
        }
    }


    /*

    <form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_top">
        <input type="hidden" name="cmd" value="_s-xclick" />
        <input type="hidden" name="hosted_button_id" value="LDHXRDGW4TBVE" />
        <input type="image" src="https://www.paypalobjects.com/fr_FR/FR/i/btn/btn_donateCC_LG.gif" border="0" name="submit" title="PayPal - The safer, easier way to pay online!" alt="Bouton Faites un don avec PayPal" />
        <img alt="" border="0" src="https://www.paypal.com/fr_FR/i/scr/pixel.gif" width="1" height="1" />
    </form>

    https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=LDHXRDGW4TBVE&source=url

*/
</script>

<style scoped>

</style>


