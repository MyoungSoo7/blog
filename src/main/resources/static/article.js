const deleteButton = document.getElementById('delete-btn');

if(deleteButton) {
    deleteButton.addEventListener('click', event => {
        let articleId = document.getElementById('article-id').value;
        fetch(`/api/articles/${articleId}`, {
            method: 'DELETE'
        }).then(() => {
            alert('삭제되었습니다.');
            location.replace('/articles');
        });
    });
}