<template>
    <div>
        <div class="line">
            <div>
                <div class="download-title">
                    <h2 class="is-size-4">Step 1</h2>
                    <p>You can download the client via our torrent, use the following button. If you don't have a torrent client we recommend the µtorrent client.</p>
                    <b-button tag="a" target="_blank" href="https://drive.google.com/open?id=1HXb_DLjf7lfjZ0Mjiqvfc3t9IlGluRfG" type="is-primary button-download">Torrent</b-button>
                    <p class="second-option-dl">You can also download the client with the following three links.</p>
                    <b-button tag="a" target="_blank" href="https://drive.google.com/open?id=1y7DUCQ20SlaE4YGU5P7mnEJv3UGWJus6" type="is-primary button-download">Part 1</b-button>
                    <b-button tag="a" target="_blank" href="https://drive.google.com/open?id=1QM6K7Nh-Lw6CWpIah11Ze0cMrM1dDdHv" type="is-primary button-download">Part 2</b-button>
                    <b-button tag="a" target="_blank" href="https://drive.google.com/open?id=1K2WWcQcDoyzo2PZQFVXi_GSBJHmpY2Mm" type="is-primary button-download">Part 3</b-button>
                    <h2 class="is-size-4">Step 2</h2>
                    <p>If you have not yet created an account we advise you to create it with the sign up button in top to be able to connect to the server once the client has been downloaded.</p>
                    <p>Now you are ready to log into the game. Enter your account name and password, create a character and you're ready to rumble!</p>
                    <p class="enjoy">Enjoy your new experience on Aion Shard server!</p>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "download"
    }
</script>

<style scoped>

    .line{
        text-align: center;
        margin-top: 5%;
        margin-bottom: 5%;
        background-color: rgba(0,0,0,0.6);
        padding: 2%;
    }

    .download-title {
        text-align: left;
        color: #f7f8fb;
    }

    .is-size-4 {
        margin-top: 1%;
    }

    .download-title p {
        margin-top: 1%;
        margin-bottom: 2%;
    }

</style>