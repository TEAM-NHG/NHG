import { localAxios } from "@/util/http-commons";

const local = localAxios();

function listArticle(param, success, fail) {
  local.get(`/companion-board`, { params: param }).then(success).catch(fail);
}

async function detailArticle(articleno, success, fail) {
  await local.get(`/companion-board/${articleno}`).then(success).catch(fail);
}

async function detailComments(articleno, success, fail) {
  await local.get(`/companion-board/${articleno}/comment`).then(success).catch(fail);
}

function registArticle(article, success, fail) {
  console.log("boardjs article", JSON.stringify(article));
  local.post(`/companion-board`, JSON.stringify(article)).then(success).catch(fail);
}

function getModifyArticle(articleno, success, fail) {
  local.get(`/companion-board/${articleno}`).then(success).catch(fail);
}

function modifyArticle(article, success, fail) {
  console.log(JSON.stringify(article))
  local.put(`/companion-board`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteArticle(articleno, success, fail) {
  local.delete(`/companion-board/${articleno}`).then(success).catch(fail);
}

export {
  listArticle,
  detailArticle,
  detailComments,
  registArticle,
  getModifyArticle,
  modifyArticle,
  deleteArticle,
};
