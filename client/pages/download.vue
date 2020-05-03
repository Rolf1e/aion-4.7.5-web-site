<template>
    <div>

        <h1 class="title"> Download Client</h1>

        <div class="columns">

            <div class="column is-half">
                <img src="http://aion-shard.com/media/download.png" alt="">
            </div>

            <div class="column is-half">
                <div class="download-title">
                    <h2 class="is-size-4">Please download the following files</h2>
                </div>

                <b-button tag="a" target="_blank" href="https://drive.google.com/open?id=1y7DUCQ20SlaE4YGU5P7mnEJv3UGWJus6" type="is-primary button-download">Part 1</b-button>
                <b-button tag="a" target="_blank" href="https://drive.google.com/open?id=1QM6K7Nh-Lw6CWpIah11Ze0cMrM1dDdHv" type="is-primary button-download">Part 2</b-button>
                <b-button tag="a" target="_blank" href="https://drive.google.com/open?id=1K2WWcQcDoyzo2PZQFVXi_GSBJHmpY2Mm" type="is-primary button-download">Part 3</b-button>

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

    .button-download {
        margin-left: 20px;
    }

    .download-title {
        margin: 18px 0px;
    }

</style>