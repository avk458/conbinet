<template>
  <div>
    <a-modal v-model="visible" :title="title" @ok="handleOk">
      <p>Some contents...</p>
      <p>Some contents...</p>
      <p>Some contents...</p>
    </a-modal>
  </div>
</template>
<script>
export default {
  data() {
    return {
      visible: false,
      title: ''
    };
  },
  methods: {
    showModal(type) {
      switch (type) {
        case 'person':
          this.title = '新增人员简介信息'
              break
        case 'performance':
          this.title = '新增业绩展示信息'
              break
      }
      this.visible = true;
    },
    handleOk(e) {
      console.log(e);
      this.visible = false;
    },
  },
};
</script>
