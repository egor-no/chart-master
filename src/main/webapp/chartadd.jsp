<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>TOP40 - Add new chart</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
    <script src="//code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script type = "text/javascript" >
        $(document).ready(function() {

            var songs = ${songs};

            $('[name="artists[]"], [name="song[]"]').autocomplete({

                minLength: 2,
                source: function (request, response) {
                    response($.map(songs, function (obj, key) {

                        var name = obj.artists + " - " + obj.name;

                        if (name.toUpperCase().indexOf(request.term.toUpperCase()) != -1) {
                            return {
                                label: obj.artists + " - " + obj.name, // Label for Display
                                value: obj // Value
                            }
                        } else {
                            return null;
                        }
                    }));
                },

                select: function(event, ui) {
                    event.preventDefault();
                    var idSong = ui.item.value.id;
                    var songName = ui.item.value.name
                    var artists = ui.item.value.artists;

                    $(this).closest('[name="position"]').find('[name="idSong[]"]').val(idSong);
                    $(this).closest('[name="position"]').find('[name="artists[]"]').val(artists);
                    $(this).closest('[name="position"]').find('[name="name[]"]').val(songName);
                }
            });

            $('[name="artists[]"], [name="song[]"]').on('input', function () {
                $(this).closest('[name="position"]').find('[name="idSong[]"]').val('');
            });

        });
    </script>
</head>
<body>
<div style="width:600px;">
    <h1>Добавление нового чарта</h1>
    <div name="menu" style="margin-bottom:5px;">
        <a href="/">Главная</a>
        |
        <b>Добавить чарт</b>
        |
        <a href="/artists">Артисты</a>
        |
        <a href="/songs">Песни</a>
    </div>
    <form method="POST" action="/chartadd">
        <table id="chart-table" style="width:600px;">
            <thead>
            <tr>
                <th style="display:none;">id</th>
                <th style="text-align: center">Mov</th>
                <th style="text-align: center">Artists</th>
                <th style="width:30px;"></th>
                <th style="text-align: center">Title</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach begin="1" end="40" var="val">
                <tr name="position">
                    <td style="display:none;"><input name="idSong[]" type="text" /></td>
                    <td style="text-align: center"><c:out value="${val}"/></td>
                    <td><input style="width:100%;" name="artists[]" type="text" /></td>
                    <td style="text-align: center"> - </td>
                    <td><input style="width:100%;" name="name[]" type="text" /></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div style="text-align: center;">
            <input style="margin-top:10px;" type="submit" value="Сохранить" />
        </div>
    </form>
</div>
</body>
</html>