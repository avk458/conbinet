<template>
  <div>
    <a-modal v-model="visible" :title="title" :maskClosable="false" @ok="handleOk" @cancel="handleCancel">
      <a-form-model :model="form" :rules="rules" ref="form">
        <a-form-model-item :label="nameLabel" prop="name">
          <a-input v-model="form.name"/>
        </a-form-model-item>
        <a-form-model-item label="描述" prop="description">
          <a-input v-model="form.description"/>
        </a-form-model-item>
        <a-form-model-item label="排序号" prop="sequence">
          <a-input-number v-model="form.sequence"/>
        </a-form-model-item>
        <a-form-model-item label="封面图片" prop="coverImageUrl">
          <Uploader v-model="form.coverImageUrl" :file-list="coverImg"/>
        </a-form-model-item>
        <a-form-model-item label="详情图片" prop="detailImageUrl">
          <Uploader v-model="form.detailImageUrl" :file-list="detailImg"/>
        </a-form-model-item>
      </a-form-model>
    </a-modal>
  </div>
</template>
<script>
import Uploader from '@/components/Uploader'
export default {
  components: {
    Uploader
  },
  data() {
    return {
      visible: false,
      title: '',
      api: '',
      nameLabel: '',
      form: {
        name: '',
        description: '',
        sequence: 0,
        coverImageUrl: '',
        detailImageUrl: ''
      },
      rules: {
        name: [{ required: true, message: '请输入', trigger: 'blur' }],
        description: [{ required: true, message: '请输入描述', trigger: 'blur' }],
        sequence: [{ required: true, type: 'number', message: '请输入排序号！', trigger: 'blur' }],
        coverImageUrl: [{ required: true, message: '请上传简介封面', trigger: 'change' }],
        detailImageUrl: [{ required: true, message: '请上传详情图片', trigger: 'change' }]
      }
    };
  },
  methods: {
    showModal(type, item) {
      switch (type) {
        case 'person':
          this.title = '新增人员简介信息'
          this.api = 'person'
          this.nameLabel = '姓名'
          this.form = item === undefined ? this.form : item
          break
        case 'performance':
          this.title = '新增业绩展示信息'
          this.api = 'performance'
          this.nameLabel = '标题'
          this.form = item === undefined ? this.form : item
          break
      }
      this.visible = true;
    },
    handleOk() {
      this.$refs.form.validate(valid => {
        if (valid) {
          console.log(this.form)
          const links = this.form._links
          if (links) {
            const url = links.self.href
            this.$axios.put(url, this.form).then(res => {
              if (200 === res.status) {
                this.visible = false;
                this.$message.success('修改成功');
                this.handleCancel();
                this.$emit('success');
              } else {
                this.$message.error('修改失败')
              }
            })
            return
          }
          this.$axios.post(this.api, this.form).then(res => {
            if (201 === res.status) {
              this.visible = false
              this.$message.success('新增成功')
              this.handleCancel()
              this.$emit('success')
            } else {
              this.$message.error('新增失败')
            }
          })
        } else {
          console.log('error submit!!')
          return false
        }
      });
    },
    handleCancel() {
      this.$refs.form.resetFields()
      if (this.form._links) {
        this.form._links = undefined
      }
      this.form.name = ''
      this.form.description = ''
      this.form.coverImageUrl = ''
      this.form.detailImageUrl = ''
      this.form.sequence = 0
    }
  },
  computed: {
    coverImg() {
      if (this.form.coverImageUrl) {
        return [{
          uid: '-1',
          name: 'image.png',
          status: 'done',
          url: this.form.coverImageUrl
        }]
      } else {
        return []
      }
    },
    detailImg() {
      if (this.form.detailImageUrl) {
        return [{
          uid: '-1',
          name: 'image.png',
          status: 'done',
          url: this.form.detailImageUrl
        }]
      } else {
        return []
      }
    }
  }
}
</script>
