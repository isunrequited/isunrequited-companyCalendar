var pageNum;
var amount;

function getPageNum() {
	if (pageNum == null || pageNum == undefined || pageNum == '' || pageNum == ' ')
		pageNum = 1;
	
	return pageNum;
}

function setPageNum(p) {
	pageNum = p
}

function getAmount() {
	if (amount == null || amount == undefined || amount == '' || amount == ' ')
		amount = 20;
	
	return amount;
}

function setAmount(a) {
	amount = a;
}
