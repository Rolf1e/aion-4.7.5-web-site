<template>
  <b-carousel :indicator-inside="false">
    <b-carousel-item v-for="(item, i) in 3" :key="i++">
            <span class="image">
              <img :src="getImgUrl(i)">
            </span>
    </b-carousel-item>
  </b-carousel>
</template>

<script>
  export default {
    methods: {
      getImgUrl(value) {
        return `http://51.178.130.119:80/media/slide${value}.png`
      }
    }
  }
</script>

<style>
  .is-active .al img {
    filter: grayscale(0%);
  }
  .al img {
    filter: grayscale(100%);
  }
</style>
