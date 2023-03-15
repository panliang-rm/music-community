<template>
  <div class="container">
    <div class="handle-box">
      <el-button @click="deleteAll">批量删除</el-button>
      <el-button type="primary" @click="centerDialogVisible = true">添加banner</el-button>
    </div>

    <el-table height="550px" border size="small" :data="data" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40" align="center"></el-table-column>
      <el-table-column label="ID" prop="id" width="50" align="center"></el-table-column>
      <el-table-column label="图片" width="102" align="center">
        <template v-slot="scope">
          <div class="swiper-img">
              <img :src="attachImageUrl(scope.row.photo)" style="width: 100%" />
          </div>
          <el-upload :action="uploadPhoto(scope.row.id)" :show-file-list="false" :on-success="handleImgSuccess" :before-upload="beforeImgUpload">
              <el-button>更新图片</el-button>
          </el-upload>          
        </template> 
      </el-table-column>
      <el-table-column label="启用" width="90" align="center">
        <template v-slot="scope">
          <div>
            {{ changeShow(scope.row.isShow) }}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150" align="center">
        <template v-slot="scope">
          <el-button type="danger" @click="deleteRow(scope.row.id)">删除</el-button>
          <el-button type="primary" v-if="scope.row.isShow === 0"
            @click="uploadMsg(scope.row.id, scope.row.isShow)">
            启用
          </el-button>
          <el-button type="primary" v-if="scope.row.isShow === 1"
            @click="uploadMsg(scope.row.id, scope.row.isShow)">
            禁用
          </el-button>
      </template>
      </el-table-column>
    </el-table>
    <el-pagination
      class="pagination"
      background
      layout="total, prev, pager, next"
      :current-page="currentPage"
      :page-size="pageSize"
      :total="tableData.length"
      @current-change="handleCurrentChange"
    >
    </el-pagination>
  </div>

  <!-- 添加 -->
<el-dialog title="添加横幅" v-model="centerDialogVisible">
  <el-form label-width="80px" :model="swiperForm" :rules="swiperRule">
      <el-form-item label="显示" prop="isShow">
        <el-radio-group v-model="swiperForm.isShow">
          <el-radio :label="0">启用</el-radio>
          <el-radio :label="1">禁用</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="横幅上传">
        <input type="file" name="file" />
      </el-form-item>
    </el-form>
    
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="centerDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addSwiper">确 定</el-button>
      </span>
    </template>
</el-dialog>

  <!-- 删除提示框 -->
  <yin-del-dialog :delVisible="delVisible" @confirm="confirm" @cancelRow="delVisible = $event"></yin-del-dialog>
</template>




<script lang="ts">
import { defineComponent, getCurrentInstance, ref, reactive, computed } from "vue";
import { HttpManager } from "@/api";
import YinDelDialog from "@/components/dialog/YinDelDialog.vue";
import mixin from "@/mixins/mixin";

export default defineComponent({
  components: {
    YinDelDialog,
  },
  setup() {
    const { proxy } = getCurrentInstance();
    const { changeShow, beforeImgUpload} = mixin();
    const tableData = ref([]); // 记录歌曲，用于显示
    const tempDate = ref([]); // 记录歌曲，用于搜索时能临时记录一份歌曲列表
    const pageSize = ref(5); // 页数
    const currentPage = ref(1); // 当前页

    // 计算当前表格中的数据
    const data = computed(() => {
      return tableData.value.slice((currentPage.value - 1) * pageSize.value, currentPage.value * pageSize.value);
    });

  

    getData();

  

    // 获取用户信息
    async function getData() {
      tableData.value = [];
      tempDate.value = [];
      const result = (await HttpManager.getAllSwiper()) as ResponseBody;
      tableData.value = result.data;
      tempDate.value = result.data;
      currentPage.value = 1;
    }
    // 获取当前页
    function handleCurrentChange(val) {
      currentPage.value = val;
    }

  //更新图片
  function uploadPhoto(id) {
    return HttpManager.attachImageUrl(`swiper/photo/update?id=${id}`)
      
  }

    function handleImgSuccess(response, file) {
    (proxy as any).$message({
      message: response.message,
      type: response.type,
    });
    if (response.success) getData();
  }


    /**
     * 添加
     */
    const centerDialogVisible = ref(false);
    const swiperForm = reactive({
     isShow: "",
     photo: "",
    });
    const swiperRule = reactive({
      isShow: [{ required: true, trigger: "change" }],
      photo: [{ required: true, trigger: "change" }],
    });

    
    async function addSwiper() {
      let params = new URLSearchParams();
      params.append("name", swiperForm.isShow);
    

      const result = (await HttpManager.getAdminOfId(params)) as ResponseBody;
      (proxy as any).$message({
        message: result.message,
        type: result.type,
      });

      if (result.success) {
        getData();
       
      }
      centerDialogVisible.value = false;
    }


//更新启用
async function uploadMsg(id, isShow) {
  try {
    let params = new URLSearchParams();
    params.append("id", id);
    if (isShow === 1) {
      params.append("isshow",'0');
    } else if(isShow === 0) {
      params.append("isshow",'1');

    }
    

    const result = (await HttpManager.updateSwiperMsg(params)) as ResponseBody;
    (proxy as any).$message({
          message: result.message,
          type: result.type,
        });
        if (result.success) getData();
      } catch (error) {
        console.error(error);
      }
}

    /**
     * 删除
     */
    const idx = ref(-1); // 记录当前要删除的行
    const multipleSelection = ref([]); // 记录当前要删除的列表
    const delVisible = ref(false); // 显示删除框

    async function confirm() {
      const result = (await HttpManager.deleteSwiper(idx.value)) as ResponseBody;
      (proxy as any).$message({
        message: result.message,
        type: result.type,
      });
      if (result) getData();
      delVisible.value = false;
    }
    function deleteRow(id) {
      idx.value = id;
      delVisible.value = true;
    }

    function handleSelectionChange(val) {
      multipleSelection.value = val;
    }
    function deleteAll() {
      for (let item of multipleSelection.value) {
        deleteRow(item.id);
        confirm();
      }
      multipleSelection.value = [];
    }

    return {
      data,
      tableData,
      delVisible,
      pageSize,
      currentPage,
      centerDialogVisible,
      swiperForm,
      swiperRule,
      addSwiper,
      deleteAll,
      handleSelectionChange,
      handleCurrentChange,
      uploadPhoto,
      handleImgSuccess,
      beforeImgUpload,
      uploadMsg,
      changeShow,
      deleteRow,
      confirm,
      attachImageUrl: HttpManager.attachImageUrl,
    };
  },
});
</script>

<style scoped></style>
