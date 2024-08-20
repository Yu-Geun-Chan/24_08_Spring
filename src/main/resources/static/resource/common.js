// a[href="#"]
// $('a[href]') : a[href="#"]를 찾겠다.
// each() : 하나하나 찾는다(순회)
// el : 당사자

// 리스트 검색타입 고르면 선택한 타입이 남겨져있게 처리하는 함수
$('select[data-value]').each(function(index, el) {
	const $el = $(el);
	
	defaultValue = $el.attr('data-value').trim(); // attr() : 속성값을 가져온다.
	
	if(defaultValue.length > 0) {
		$el.val(defaultValue);
	}
});
