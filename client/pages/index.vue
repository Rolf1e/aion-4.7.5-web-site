<template>

  <div class="content">

    <div class="columns">

      <div class="column text-content">

        <h1 class="title"> Welcome to Aion Shard </h1>
        <p> Come and discover a server with a multitude of custom features.<br>
          A pvp energized and encouraged, pve instances reworked so that you can rediscover the feeling of novelty. A team listening to you and much more!</p>

        <div class="buttons">
          <b-button type="is-primary">Join the server</b-button>
        </div>

      </div>

    </div>

  </div>

</template>

<script>

  import Carousel from "~/components/Carousel"
  import Card from "~/components/Card"

  export default {
    name: 'Home',

    components: {
      Carousel,
      Card
    },
  }
</script>

<style>

  .content {
    background-color: rgba(0,0,0,0.6);
    /*border: solid rgba(127,0,255,0.2);*/
    margin-top: 7%;
    margin-left: 7%;
    padding: 2%;
    width: 60%;
  }

  .content .columns .title {
    font-size: 250%;
    color: #f7f8fb;
  }

  .text-content p {
    margin-top: 5%;
    text-justify: auto;
    color: #f7f8fb;
  }

  .text-content .buttons {
    margin-top: 5%;
  }

</style>
