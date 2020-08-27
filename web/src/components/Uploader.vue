<template>
  <div class="clearfix">
    <a-upload
        :action="uploadApi"
        list-type="picture-card"
        accept="image/*"
        :file-list="fileList"
        :beforeUpload="handleBeforeUpload"
        @preview="handlePreview"
        @change="handleChange"
    >
      <div v-if="fileList.length === 0">
        <a-icon type="plus"/>
        <div class="ant-upload-text">
          Upload
        </div>
      </div>
    </a-upload>
    <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
      <img alt="example" style="width: 100%" :src="previewImage"/>
    </a-modal>
  </div>
</template>
<script>
function getBase64(file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = error => reject(error);
  });
}

export default {
  data() {
    return {
      previewVisible: false,
      previewImage: ''
    }
  },
  model: {
    event: 'update'
  },
  props: ['fileList'],
  methods: {
    handleCancel() {
      this.previewVisible = false;
    },
    async handlePreview(file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj);
      }
      this.previewImage = file.url || file.preview;
      this.previewVisible = true;
    },
    handleChange({file, fileList}) {
      const status = file.status
      if ('done' === status) {
        this.$emit('update', file.response)
      }
      if ('error' === status) {
        this.$message.error('上传失败')
        return
      }
      this.fileList = fileList;
    },
    handleBeforeUpload(file) {
      if (file && file.size > 1024 * 1024 * 5) {
        this.$message.warning('上传图片被限制在小于5MB')
        return false
      }
      return true
    }
  },
  computed: {
    uploadApi() {
      const baseUrl = process.env.NODE_ENV === 'production' ? '/cabinet-server/' : '/';
      return baseUrl + 'file'
    }
  }
};
</script>
<style>
/* you can make up upload button and sample style by using stylesheets */
.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666;
}
</style>
