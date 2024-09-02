export default {
    namespaced: true,
    state: {
        news: [
            {
                id: 767634786,
                date: "12.02.2005",
                title: "News 1",
                img: "https://avatars.mds.yandex.net/i?id=905ddc4ffc1ff628b779c9b2661afa02_l-5312449-images-thumbs&n=13",
                text: `rterterterteyhrtyrtyrty ryuityerur rtueyruyeruitu
                rty ueirryitryeeuriiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiity ufdh idf goyogiuyrytyreuiyiriteirterutyr`
            },
            {
                id: 6342433,
                title: "News 2",
                date: "12.02.2012",
                img: "https://avatars.mds.yandex.net/i?id=905ddc4ffc1ff628b779c9b2661afa02_l-5312449-images-thumbs&n=13",
                text: `dfguiegyuerue ureytuiyeruigyreig;vhqdsufoguoreu uruyeruygfh
                tyrtyrtyrtyvbngrtyhujys
                yhjsyhkjhkjhfg
                rththhrjthg;j
                dgjfjgdjgjdf\n
                er uutierytuyrit`
            }
        ]
    },
    getters: {
        getSortNews: (state) => {
            return state.news.sort((a, b) => {
                let aDate = new Date(a.date);
                let bDate = new Date(b.date);
                return bDate - aDate;
            });
        }
    }
}
  