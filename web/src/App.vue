<template>
  <div id="app">
    <a-row :gutter="8">
      <a-col :span="2"/>
      <a-col :md="6" :lg="10">
        <ObjectCard title="人员简介"/>
      </a-col>
      <a-col :md="6" :lg="10">
        <ObjectCard title="业绩展示"/>
      </a-col>
      <a-col :span="2"/>
    </a-row>
  </div>
</template>

<script>
import ObjectCard from './components/ObjectCard.vue'

export default {
  name: 'App',
  components: {
    ObjectCard
  }
}
</script>

<style>
#app{
  margin: 100px 0 0 0;
}
</style>
