<%@ page pageEncoding="UTF-8" %>

<link rel="icon" href="/icons/icon.png" type="image/x-icon">
<link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<style><%@include file="/css/style.css"%></style>
<script>
    window.__ci = ${sessionScope.chartInfoId != null ? sessionScope.chartInfoId : "null"};

    async function copyText(text) {
        try {
            await navigator.clipboard.writeText(text);
            showShareToast("Ссылка скопирована");
        } catch (e) {
            const ta = document.createElement("textarea");
            ta.value = text;
            ta.style.position = "fixed";
            ta.style.left = "-9999px";
            document.body.appendChild(ta);
            ta.focus();
            ta.select();
            try { document.execCommand("copy"); } catch (ignored) {}
            document.body.removeChild(ta);
            showShareToast("Ссылка скопирована");
        }
    }

    function buildShareUrlWithCi() {
        const url = new URL(window.location.href);

        if (url.searchParams.get("ci")) return url.toString();

        if (window.__ci) {
            url.searchParams.set("ci", window.__ci);
        }

        return url.toString();
    }

    function copyShareLink() {
        return copyText(buildShareUrlWithCi());
    }

    function copyProfileLink(profileUserId) {
        const base = window.location.origin;
        return copyText(base + "/profile?u=" + profileUserId);
    }

    function showShareToast(text) {
        let t = document.getElementById("share-toast");
        if (!t) {
            t = document.createElement("div");
            t.id = "share-toast";
            t.className = "toast-win no-display";
            document.body.appendChild(t);
        }

        t.textContent = text;
        t.classList.remove("no-display");

        clearTimeout(window.__shareToastTimer);
        window.__shareToastTimer = setTimeout(() => {
            t.classList.add("no-display");
        }, 1200);
    }
</script>